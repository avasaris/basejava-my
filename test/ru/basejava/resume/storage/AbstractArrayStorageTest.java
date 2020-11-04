package ru.basejava.resume.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.basejava.resume.exception.StorageException;
import ru.basejava.resume.model.Resume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            while (storage.size() < AbstractArrayStorage.CAPACITY) {
                storage.save(new Resume("Test Test"));
            }
        } catch (StorageException e) {
            Assert.fail("Unexpected storage overflow");
        }
        storage.save(new Resume("Test Test"));
    }
}
