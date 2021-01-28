package ru.basejava.resume.storage.serializer;

import ru.basejava.resume.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    public static final String NULL_HOLDER = "STRING_IS_NULL";

    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            resumeHeadersSave(dos, resume.getUuid(), resume.getFullName());
            resumeContactsSave(dos, resume.getContacts());
            resumeSectionsSave(dos, resume.getSections());
        }
    }

    private void resumeHeadersSave(DataOutputStream dos, String... strings) throws IOException {
        writeCollectionWithException(dos, dos::writeUTF, Arrays.asList(strings));
    }

    private void resumeContactsSave(DataOutputStream dos, Map<ContactType, String> contacts) throws IOException {
        dos.writeInt(contacts.size());
        writeCollectionWithException(
                dos,
                x -> writeCollectionWithException(dos, dos::writeUTF, Arrays.asList(x.getKey().name(), x.getValue())),
                contacts.entrySet()
        );
    }

    private void resumeSectionsSave(DataOutputStream dos, Map<SectionType, Section> sections) throws IOException {
        dos.writeInt(sections.size());
        writeCollectionWithException(
                dos,
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
                            writeCollectionWithException(dos, dos::writeUTF, lists);
                            break;
                        case EDUCATION:
                        case EXPERIENCE:
                            List<Organisation> organisations = ((OrganisationSection) entry.getValue()).getOrganisations();
                            dos.writeInt(organisations.size());
                            writeCollectionWithException(
                                    dos,
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
        writeCollectionWithException(dos, dos::writeUTF, Arrays.asList(org.getLink().getName(), org.getLink().getUrl()));
        List<Organisation.Position> positions = org.getPositions();
        dos.writeInt(positions.size());
        writeCollectionWithException(
                dos,
                x -> writeCollectionWithException(dos, dos::writeUTF,
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
            Resume resume = new Resume(readUtfWithNull(dis), readUtfWithNull(dis));
            readWithException(dis, () -> resume.addContact(ContactType.valueOf(readUtfWithNull(dis)), readUtfWithNull(dis)));

            readWithException(dis, () -> {
                SectionType sectionType = SectionType.valueOf(readUtfWithNull(dis));
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        TextSection ts = new TextSection();
                        readWithException(dis, () -> ts.add(readUtfWithNull(dis)));
                        resume.addSection(sectionType, ts);
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListSection ls = new ListSection();
                        readWithException(dis, () -> ls.add(readUtfWithNull(dis)));
                        resume.addSection(sectionType, ls);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        OrganisationSection orgSection = new OrganisationSection();
                        readWithException(dis, () -> {
                            Organisation organisation = new Organisation();
                            organisation.setLink(new LinksList.Link(readUtfWithNull(dis), readUtfWithNull(dis)));
                            readWithException(dis, () -> {
                                Organisation.Position position = new Organisation.Position();
                                position.setBegin(YearMonth.parse(readUtfWithNull(dis)));
                                position.setEnd(YearMonth.parse(readUtfWithNull(dis)));
                                position.setHeader(readUtfWithNull(dis));
                                position.setDescription(readUtfWithNull(dis));
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

    private String readUtfWithNull(DataInputStream dis) throws IOException {
        String retStr = dis.readUTF();
        if (retStr.equals(NULL_HOLDER)) retStr = null;
        return retStr;
    }

    private <T> void writeCollectionWithException(DataOutputStream dos, GenericFuncInterfaceWithExceptions<T> dsw, Collection<T> elements) throws IOException {
        for (T element : elements) {
            if (element == null)
                dos.writeUTF(NULL_HOLDER);
            else
                dsw.run(element);
        }
    }

    private void readWithException(DataInputStream dis, RunWithException lambda) throws IOException {
        int count = dis.readInt();
        for (int i = 0; i < count; i++) {
            lambda.run();
        }
    }

    interface GenericFuncInterfaceWithExceptions<T> {
        void run(T t) throws IOException;
    }

    interface RunWithException {
        void run() throws IOException;
    }
}
