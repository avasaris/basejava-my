package ru.basejava.resume.storage;

public class ObjectStreamPathStorage extends AbstractPathStorage {
    protected ObjectStreamPathStorage(String directory) {
        super(directory, new ObjectStreamStorage());
    }

}
