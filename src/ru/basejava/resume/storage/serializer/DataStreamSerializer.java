package ru.basejava.resume.storage.serializer;

import ru.basejava.resume.exception.StorageException;
import ru.basejava.resume.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.*;

interface WriteElemWithException<T, S> {
    void accept(T element, S dos) throws IOException;
}

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(OutputStream os, Resume resume) {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            writeWithException(dos, (str, s) -> s.writeUTF(str), Arrays.asList(resume.getUuid(), resume.getFullName()));
            DsContactsSave(dos, resume.getContacts());
            DsSectionsSave(dos, resume.getSections());
        } catch (IOException e) {
            throw new StorageException("DataStream write error", "", e);
        }
    }

    private void writeWithException(DataOutputStream dos, WriteElemWithException<String, DataOutputStream> wse, List<String> strings) throws IOException {
        dos.writeInt(strings.size());
        for (String string : strings) {
            wse.accept(string, dos);
        }
    }

    private void DsSectionsSave(DataOutputStream dos, Map<SectionType, Section> sections) throws IOException {
        dos.writeInt(sections.size());
        for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
            dos.writeUTF(entry.getKey().name());
            switch (entry.getKey()) {
                case PERSONAL:
                case OBJECTIVE:
                    writeWithException(dos, (str, s) -> s.writeUTF(str), Collections.singletonList(((TextSection) entry.getValue()).getContent()));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    writeWithException(dos, (str, s) -> s.writeUTF(str), ((ListSection) entry.getValue()).getItems());
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    List<Organisation> organisations = ((OrganisationSection) entry.getValue()).getOrganisations();
                    dos.writeInt(organisations.size());
                    for (Organisation organisation : organisations) {
                        DsOrganisationSave(dos, organisation);
                    }
                    break;
            }
        }
    }

    private void DsContactsSave(DataOutputStream dos, Map<ContactType, String> contacts) throws IOException {
        dos.writeInt(contacts.size());
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            writeWithException(dos, (str, s) -> s.writeUTF(str), Arrays.asList(entry.getKey().name(), entry.getValue()));
        }
    }

    private void DsOrganisationSave(DataOutputStream dos, Organisation org) throws IOException {
        writeWithException(dos, (str, s) -> s.writeUTF(str), Arrays.asList(org.getLink().getName(), org.getLink().getUrl()));

        List<Organisation.Position> positions = org.getPositions();
        dos.writeInt(positions.size());

        for (Organisation.Position position : positions) {
            writeWithException(dos, (str, s) -> s.writeUTF(str), Arrays.asList(position.getBegin().toString(),
                    position.getEnd().toString(),
                    position.getHeader(),
                    position.getDescription()));
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            dis.readInt();
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            resume.setContacts(DsContactsRestore(dis));
            resume.setSections(DsSectionsRestore(dis));
            return resume;
        }
    }

    private Map<SectionType, Section> DsSectionsRestore(DataInputStream dis) throws IOException {
        Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);
        int sectionsCount = dis.readInt();
        for (int i = 0; i < sectionsCount; i++) {
            SectionType sectionType = SectionType.valueOf(dis.readUTF());
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    sections.put(sectionType, DsStringsSectionRestore(dis, new TextSection()));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    sections.put(sectionType, DsStringsSectionRestore(dis, new ListSection()));
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

    private Section DsStringsSectionRestore(DataInputStream dis, Section section) throws IOException {
        int count = dis.readInt();
        for (int j = 0; j < count; j++) {
            section.addItem(dis.readUTF());
        }
        return section;
    }

    private Map<ContactType, String> DsContactsRestore(DataInputStream dis) throws IOException {
        Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
        int contactsCount = dis.readInt();
        for (int i = 0; i < contactsCount; i++) {
            dis.readInt();
            contacts.put(ContactType.valueOf(dis.readUTF()), dis.readUTF());
        }
        return contacts;
    }
}
