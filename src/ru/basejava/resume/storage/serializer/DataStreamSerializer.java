package ru.basejava.resume.storage.serializer;

import ru.basejava.resume.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.*;

interface ElementWriterWithException<T> {
    void accept(T t) throws IOException;
}

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dsHeadersSave(dos, resume.getUuid(), resume.getFullName());
            dsContactsSave(dos, resume.getContacts());
            dsSectionsSave(dos, resume.getSections());
        }
    }

    private void dsHeadersSave(DataOutputStream dos, String uuid, String fullName) throws IOException {
        writeWithException(dos, dos::writeUTF, Arrays.asList(uuid, fullName));
    }

    private void dsContactsSave(DataOutputStream dos, Map<ContactType, String> contacts) throws IOException {
        dos.writeInt(contacts.size());
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            writeWithException(dos, dos::writeUTF, Arrays.asList(entry.getKey().name(), entry.getValue()));
        }
    }

    private void dsSectionsSave(DataOutputStream dos, Map<SectionType, Section> sections) throws IOException {
        dos.writeInt(sections.size());
        for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
            dos.writeUTF(entry.getKey().name());
            switch (entry.getKey()) {
                case PERSONAL:
                case OBJECTIVE:
                    writeWithException(dos, dos::writeUTF, Collections.singletonList(((TextSection) entry.getValue()).getContent()));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    writeWithException(dos, dos::writeUTF, ((ListSection) entry.getValue()).getItems());
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    List<Organisation> organisations = ((OrganisationSection) entry.getValue()).getOrganisations();
                    dos.writeInt(organisations.size());
                    for (Organisation organisation : organisations) {
                        dsOrganisationSave(dos, organisation);
                    }
                    break;
            }
        }
    }

    private void dsOrganisationSave(DataOutputStream dos, Organisation org) throws IOException {
        writeWithException(dos, dos::writeUTF, Arrays.asList(org.getLink().getName(), org.getLink().getUrl()));

        List<Organisation.Position> positions = org.getPositions();
        dos.writeInt(positions.size());

        for (Organisation.Position position : positions) {
            writeWithException(dos, dos::writeUTF, Arrays.asList(position.getBegin().toString(),
                    position.getEnd().toString(),
                    position.getHeader(),
                    position.getDescription()));
        }
    }

    private <T> void writeWithException(DataOutputStream dos, ElementWriterWithException<T> wse, Collection<T> elements) throws IOException {
        dos.writeInt(elements.size());
        for (T element : elements) {
            wse.accept(element);
        }
    }





    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            dis.readInt();
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            resume.setContacts(dsContactsRestore(dis));
            resume.setSections(dsSectionsRestore(dis));
            return resume;
        }
    }

    private Map<SectionType, Section> dsSectionsRestore(DataInputStream dis) throws IOException {
        Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);
        int sectionsCount = dis.readInt();
        for (int i = 0; i < sectionsCount; i++) {
            SectionType sectionType = SectionType.valueOf(dis.readUTF());
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    sections.put(sectionType, dsStringsSectionRestore(dis, new TextSection()));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    sections.put(sectionType, dsStringsSectionRestore(dis, new ListSection()));
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    OrganisationSection orgSection = new OrganisationSection();
                    int orgSectionCount = dis.readInt();
                    for (int j = 0; j < orgSectionCount; j++) {
                        Organisation organisation = new Organisation();
                        dis.readInt();
                        organisation.setLink(new LinksList.Link(dis.readUTF(), dis.readUTF()));
                        int positionCount = dis.readInt();
                        for (int p = 0; p < positionCount; p++) {
                            dis.readInt();
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

    private Section dsStringsSectionRestore(DataInputStream dis, Section section) throws IOException {
        int count = dis.readInt();
        for (int j = 0; j < count; j++) {
            section.addItem(dis.readUTF());
        }
        return section;
    }

    private Map<ContactType, String> dsContactsRestore(DataInputStream dis) throws IOException {
        Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
        int contactsCount = dis.readInt();
        for (int i = 0; i < contactsCount; i++) {
            dis.readInt();
            contacts.put(ContactType.valueOf(dis.readUTF()), dis.readUTF());
        }
        return contacts;
    }
}
