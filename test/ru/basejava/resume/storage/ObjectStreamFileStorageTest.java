package ru.basejava.resume.storage;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {
    public ObjectStreamFileStorageTest() {
        super(new ObjectStreamFileStorage(STORAGE_DIR));
    }

    private static class ObjectStreamFileStorage extends AbstractFileStorage {
        protected ObjectStreamFileStorage(String directory) {
            super(directory, new ObjectStreamStorage());
        }

    }
}
