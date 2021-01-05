package ru.basejava.resume.storage.serializer;

import ru.basejava.resume.model.ContactType;
import ru.basejava.resume.model.Resume;
import ru.basejava.resume.model.Section;
import ru.basejava.resume.model.SectionType;

import java.io.*;
import java.util.EnumMap;
import java.util.Map;

public class DataStreamSerializer implements SerializerStrategy {
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
            // TODO implements sections

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
                ContactType ct = ContactType.valueOf(dis.readUTF());
                String st = dis.readUTF();
                contacts.put(ct, st);
            }
            resume.setContacts(contacts);

            // TODO implements sections
            Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);
            resume.setSections(sections);

            return resume;
        }
    }
}
