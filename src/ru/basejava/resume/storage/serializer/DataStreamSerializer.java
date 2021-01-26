package ru.basejava.resume.storage.serializer;

import ru.basejava.resume.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            resumeHeadersSave(dos, resume.getUuid(), resume.getFullName());
            resumeContactsSave(dos, resume.getContacts());
            resumeSectionsSave(dos, resume.getSections());
        }
    }

    private void resumeHeadersSave(DataOutputStream dos, String... strings) throws IOException {
        writeCollectionWithException(dos::writeUTF, Arrays.asList(strings));
    }

    private void resumeContactsSave(DataOutputStream dos, Map<ContactType, String> contacts) throws IOException {
        dos.writeInt(contacts.size());
        writeCollectionWithException(
                x -> writeCollectionWithException(dos::writeUTF, Arrays.asList(x.getKey().name(), x.getValue())),
                contacts.entrySet()
        );
    }

    private void resumeSectionsSave(DataOutputStream dos, Map<SectionType, Section> sections) throws IOException {
        dos.writeInt(sections.size());
        writeCollectionWithException(
                entry -> {
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
                },
                sections.entrySet()
        );
    }

    private void resumeOrganisationSave(DataOutputStream dos, Organisation org) throws IOException {
        writeCollectionWithException(dos::writeUTF, Arrays.asList(org.getLink().getName(), org.getLink().getUrl()));
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
            readWithException(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));

            readWithException(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        TextSection ts = new TextSection();
                        readWithException(dis, () -> ts.add(dis.readUTF()));
                        resume.addSection(sectionType, ts);
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListSection ls = new ListSection();
                        readWithException(dis, () -> ls.add(dis.readUTF()));
                        resume.addSection(sectionType, ls);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        OrganisationSection orgSection = new OrganisationSection();
                        readWithException(dis, () -> {
                            Organisation organisation = new Organisation();
                            organisation.setLink(new LinksList.Link(dis.readUTF(), dis.readUTF()));
                            readWithException(dis, () -> {
                                Organisation.Position position = new Organisation.Position();
                                position.setBegin(YearMonth.parse(dis.readUTF()));
                                position.setEnd(YearMonth.parse(dis.readUTF()));
                                position.setHeader(dis.readUTF());
                                position.setDescription(dis.readUTF());
                                organisation.addPosition(position);
                            });
                            orgSection.add(organisation);
                        });
                        resume.addSection(sectionType, orgSection);
                        break;
                }
            });
            return resume;
        }
    }

    private <T> void writeCollectionWithException(DataStreamWriterWithException<T> dsw, Collection<T> elements) throws IOException {
        for (T element : elements) {
            dsw.write(element);
        }
    }

    private void readWithException(DataInputStream dis, RunWithException lambda) throws IOException {
        int contactsCount = dis.readInt();
        for (int i = 0; i < contactsCount; i++) {
            lambda.run();
        }
    }

    interface DataStreamWriterWithException<T> {
        void write(T t) throws IOException;
    }

    interface RunWithException {
        void run() throws IOException;
    }
}
