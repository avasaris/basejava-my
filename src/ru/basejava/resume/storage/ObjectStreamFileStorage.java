package ru.basejava.resume.storage;

public class ObjectStreamFileStorage extends AbstractFileStorage {
    protected ObjectStreamFileStorage(String directory) {
        super(directory, new ObjectStreamStorage());
    }

}
