package ru.basejava.resume.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.basejava.resume.exception.ExistStorageException;
import ru.basejava.resume.exception.NotExistStorageException;
import ru.basejava.resume.exception.StorageException;
import ru.basejava.resume.model.Resume;

import java.util.UUID;

public abstract class AbstractArrayStorageTest {
    private static final String UUID1 = UUID.randomUUID().toString();
    private static final String UUID2 = UUID.randomUUID().toString();
    private static final String UUID3 = UUID.randomUUID().toString();
    final Resume resume1 = new Resume(UUID1);
    final Resume resume2 = new Resume(UUID2);
    final Resume resume3 = new Resume(UUID3);

    final Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void init() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void get() {
        Assert.assertEquals(resume1, storage.get(UUID1));
        Assert.assertEquals(resume2, storage.get(UUID2));
        Assert.assertEquals(resume3, storage.get(UUID3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID.randomUUID().toString());
    }

    @Test
    public void update() {
        Resume resumeForUpdate = new Resume(UUID1);
        storage.update(resumeForUpdate);
        Assert.assertEquals(resumeForUpdate, storage.get(UUID1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume());
    }

    @Test
    public void save() {
        String uuidForSave = UUID.randomUUID().toString();
        Resume resumeForSave = new Resume(uuidForSave);
        storage.save(resumeForSave);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(resumeForSave, storage.get(uuidForSave));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(resume1);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            while (storage.size() < AbstractArrayStorage.CAPACITY) {
                storage.save(new Resume());
            }
        } catch(StorageException e) {
            Assert.fail("Unexpected storage overflow");
        }
        storage.save(new Resume());
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID2);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID.randomUUID().toString());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertArrayEquals(storage.getAll(), new Resume[]{});
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }
}