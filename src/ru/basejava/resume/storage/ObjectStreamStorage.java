package ru.basejava.resume.storage;

import ru.basejava.resume.model.Resume;

import java.io.*;

public class ObjectStreamStorage extends AbstractFileStorage {
    protected ObjectStreamStorage(File directory) {
        super(directory);
    }

    @Override
    protected void doWrite(OutputStream os, Resume resume) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(oos);
        }
    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {
        return null;
    }
}
