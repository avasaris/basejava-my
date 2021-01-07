package ru.basejava.resume.storage.serializer;

import ru.basejava.resume.exception.StorageException;
import ru.basejava.resume.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(OutputStream os, Resume resume) {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            DsStringsSave(dos, resume.getUuid(), resume.getFullName());
            DsContactsSave(dos, resume.getContacts());
            DsSectionsSave(dos, resume.getSections());
        } catch (IOException e) {
            throw new StorageException("DataStream write error", "", e);
        }
    }

    private void DsSectionsSave(DataOutputStream dos, Map<SectionType, Section> sections) throws IOException {
        dos.writeInt(sections.size());
        for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
            dos.writeUTF(entry.getKey().name());
            dos.writeInt(entry.getValue().size());
            switch (entry.getKey()) {
                case PERSONAL:
                case OBJECTIVE:
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    entry.getValue().getItemsStream().forEach(x -> DsStringsSave(dos, (String) x));
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    entry.getValue().getItemsStream().forEach(x -> DsOrganisationSave(dos, (Organisation) x));
                    break;
            }
        }
    }

    private void DsContactsSave(DataOutputStream dos, Map<ContactType, String> contacts) throws IOException {
        dos.writeInt(contacts.size());
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            DsStringsSave(dos, entry.getKey().name(), entry.getValue());
        }
    }

    private void DsOrganisationSave(DataOutputStream dos, Organisation org) {
        try {
            DsStringsSave(dos, org.getLink().getName(), org.getLink().getUrl());

            List<Organisation.Position> positions = org.getPositions();
            dos.writeInt(positions.size());

            for (Organisation.Position position : positions) {
                DsStringsSave(dos,
                        position.getBegin().toString(),
                        position.getEnd().toString(),
                        position.getHeader(),
                        position.getDescription());
            }
        } catch (IOException e) {
            throw new StorageException("DataStream write error", "", e);
        }
    }

    private void DsStringsSave(DataOutputStream dos, String... strings) {
        try {
            for (String str : strings) {
                dos.writeUTF(str);
            }
        } catch (IOException e) {
            throw new StorageException("DataStream write error", "", e);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
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
                    Section<Organisation> orgSection = new OrganisationSection();
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
                        orgSection.addItem(organisation);
                    }
                    sections.put(sectionType, orgSection);
                    break;
            }
        }
        return sections;
    }

    private Section<String> DsStringsSectionRestore(DataInputStream dis, Section<String> section) throws IOException {
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
            contacts.put(ContactType.valueOf(dis.readUTF()), dis.readUTF());
        }
        return contacts;
    }


}
