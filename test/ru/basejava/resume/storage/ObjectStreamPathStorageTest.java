package ru.basejava.resume.storage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new ObjectStreamPathStorage(STORAGE_DIR));
    }

    private static class ObjectStreamPathStorage extends AbstractPathStorage {
        protected ObjectStreamPathStorage(String directory) {
            super(directory, new ObjectStreamStorage());
        }

    }
}