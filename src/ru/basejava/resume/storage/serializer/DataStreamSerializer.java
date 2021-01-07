package ru.basejava.resume.storage.serializer;

import ru.basejava.resume.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            System.out.println(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                System.out.println(entry.getKey().name());
                dos.writeInt(entry.getValue().size());
                System.out.println(entry.getValue().size());
                switch (entry.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        entry.getValue().getItemsStream().forEach(x -> {
                            try {
                                dos.writeUTF((String) x);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        entry.getValue().getItemsStream().forEach(System.out::println);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        entry.getValue().getItemsStream().forEach(x -> OrganisationDataSerialization(dos, (Organisation) x));
                        break;
                }

                System.out.println("-----------------------");
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();

            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
            for (int i = 0; i < size; i++) {
                ContactType contactType = ContactType.valueOf(dis.readUTF());
                String content = dis.readUTF();
                contacts.put(contactType, content);
            }
            resume.setContacts(contacts);

            // TODO implements sections
            Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);
            for (int i = 0; i < dis.readInt(); i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());

                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dis.readInt();
                        sections.put(sectionType, new TextSection(dis.readUTF()));
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        Section<String> listSection = new ListSection();
                        for (int j = 0; j < dis.readInt(); j++) {
                            listSection.addItem(dis.readUTF());
                        }
                        sections.put(sectionType, listSection);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        Section<Organisation> orgSection = new OrganisationSection();
                        for (int j = 0; j < dis.readInt(); j++) {
                            Organisation organisation = new Organisation();
                            LinksList.Link link = new LinksList.Link(dis.readUTF(), dis.readUTF());
                            organisation.setLink(link);
                            for (int p = 0; p < dis.readInt(); p++) {
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
            resume.setSections(sections);

            return resume;
        }
    }

    private void OrganisationDataSerialization(DataOutputStream dos, Organisation org) {
        try {
            dos.writeUTF(org.getLink().getName());
            System.out.println(org.getLink().getName());
            dos.writeUTF(org.getLink().getUrl());
            System.out.println(org.getLink().getUrl());

            List<Organisation.Position> positions = org.getPositions();
            dos.writeInt(positions.size());
            System.out.println(positions.size());

            for (Organisation.Position position : positions) {
                dos.writeUTF(position.getBegin().toString());
                System.out.println(position.getBegin());
                dos.writeUTF(position.getEnd().toString());
                System.out.println(position.getEnd());
                dos.writeUTF(position.getHeader());
                System.out.println(position.getHeader());
                dos.writeUTF(position.getDescription());
                System.out.println(position.getDescription());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
