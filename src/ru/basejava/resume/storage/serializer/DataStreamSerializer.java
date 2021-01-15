package ru.basejava.resume.storage.serializer;

import ru.basejava.resume.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.*;

public class DataStreamSerializer implements StreamSerializer {

    interface DataStreamWriterWithException<T> {
        void accept(T t) throws IOException;
    }

    private <T> void writeCollectionWithException(DataStreamWriterWithException<T> dsw, Collection<T> elements) throws IOException {
        for (T element : elements) {
            dsw.accept(element);
        }
    }

    private <T> void writeCollectionWithException(DataStreamWriterWithException<T> dsw, T... elements) throws IOException {
        for (T element : elements) {
            dsw.accept(element);
        }
    }

    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            resumeHeadersSave(dos, resume.getUuid(), resume.getFullName());
            resumeContactsSave(dos, resume.getContacts());
            resumeSectionsSave(dos, resume.getSections());
        }
    }

    private void resumeHeadersSave(DataOutputStream dos, String... strings) throws IOException {
        writeCollectionWithException(dos::writeUTF, strings);
    }

    private void resumeContactsSave(DataOutputStream dos, Map<ContactType, String> contacts) throws IOException {
        dos.writeInt(contacts.size());
        writeCollectionWithException(
                x -> writeCollectionWithException(dos::writeUTF, x.getKey().name(), x.getValue()),
                contacts.entrySet()
        );
    }

    private void resumeSectionsSave(DataOutputStream dos, Map<SectionType, Section> sections) throws IOException {
        dos.writeInt(sections.size());
        for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
            dos.writeUTF(entry.getKey().name());
            switch (entry.getKey()) {
                case PERSONAL:
                case OBJECTIVE:
                    dos.writeInt(1);
                    dos.writeUTF(((TextSection) entry.getValue()).getContent());
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    List<String> lists = ((ListSection) entry.getValue()).getItems();
                    dos.writeInt(lists.size());
                    writeCollectionWithException(dos::writeUTF, lists);
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    List<Organisation> organisations = ((OrganisationSection) entry.getValue()).getOrganisations();
                    dos.writeInt(organisations.size());
                    writeCollectionWithException(
                            x -> resumeOrganisationSave(dos, x),
                            organisations
                    );
                    break;
            }
        }
    }

    private void resumeOrganisationSave(DataOutputStream dos, Organisation org) throws IOException {
        writeCollectionWithException(dos::writeUTF, org.getLink().getName(), org.getLink().getUrl());

        List<Organisation.Position> positions = org.getPositions();
        dos.writeInt(positions.size());

        writeCollectionWithException(
                x -> writeCollectionWithException(dos::writeUTF,
                        Arrays.asList(x.getBegin().toString(),
                                x.getEnd().toString(),
                                x.getHeader(),
                                x.getDescription())),
                positions
        );
    }


    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            resume.setContacts(resumeContactsRestore(dis));
            resume.setSections(resumeSectionsRestore(dis));
            return resume;
        }
    }

    private Map<ContactType, String> resumeContactsRestore(DataInputStream dis) throws IOException {
        Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
        int contactsCount = dis.readInt();
        for (int i = 0; i < contactsCount; i++) {
            contacts.put(ContactType.valueOf(dis.readUTF()), dis.readUTF());
        }
        return contacts;
    }

    private Map<SectionType, Section> resumeSectionsRestore(DataInputStream dis) throws IOException {
        Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);
        int sectionsCount = dis.readInt();

        for (int i = 0; i < sectionsCount; i++) {

            SectionType sectionType = SectionType.valueOf(dis.readUTF());
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    sections.put(sectionType, resumeStringsSectionRestore(dis, new TextSection()));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    sections.put(sectionType, resumeStringsSectionRestore(dis, new ListSection()));
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    OrganisationSection orgSection = new OrganisationSection();
                    int orgSectionCount = dis.readInt();
                    for (int j = 0; j < orgSectionCount; j++) {
                        Organisation organisation = new Organisation();
                        organisation.setLink(new LinksList.Link(dis.readUTF(), dis.readUTF()));
                        int positionCount = dis.readInt();
                        for (int p = 0; p < positionCount; p++) {
                            Organisation.Position position = new Organisation.Position();
                            position.setBegin(YearMonth.parse(dis.readUTF()));
                            position.setEnd(YearMonth.parse(dis.readUTF()));
                            position.setHeader(dis.readUTF());
                            position.setDescription(dis.readUTF());
                            organisation.addPosition(position);
                        }
                        orgSection.addItemOrg(organisation);
                    }
                    sections.put(sectionType, orgSection);
                    break;
            }
        }
        return sections;
    }

    private Section resumeStringsSectionRestore(DataInputStream dis, Section section) throws IOException {
        int count = dis.readInt();
        for (int j = 0; j < count; j++) {
            section.addItem(dis.readUTF());
        }
        return section;
    }

}
