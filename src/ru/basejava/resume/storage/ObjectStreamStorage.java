package ru.basejava.resume.storage;

import ru.basejava.resume.exception.StorageException;
import ru.basejava.resume.model.Resume;

import java.io.*;

public class ObjectStreamStorage implements StreamStorageStrategy {
    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(resume);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Can't read resume from InputStream.", "", e);
        }
    }
}
