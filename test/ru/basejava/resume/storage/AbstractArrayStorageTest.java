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
    private final String UUID1 = UUID.randomUUID().toString();
    private final String UUID2 = UUID.randomUUID().toString();
    private final String UUID3 = UUID.randomUUID().toString();
    final Resume resume1 = new Resume(UUID1);
    final Resume resume2 = new Resume(UUID2);
    final Resume resume3 = new Resume(UUID3);

    final Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setStorage() {
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
        String updatedUuid = UUID1;
        Resume updatedResume = new Resume(updatedUuid);
        storage.update(updatedResume);
        Assert.assertEquals(storage.get(updatedUuid), updatedResume);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume updatedResume = new Resume();
        storage.update(updatedResume);
    }

    @Test
    public void save() {
        storage.save(new Resume());
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(resume1);
    }

    @Test
    public void saveOverflow() {
        while (storage.size() < AbstractArrayStorage.CAPACITY) {
            storage.save(new Resume());
        }
        try {
            storage.save(new Resume());
        } catch (StorageException e) {
            return;
        }
        Assert.fail("Unknown storage error");
    }

    @Test
    public void delete() {
        storage.delete(UUID2);
        Assert.assertEquals(2, storage.size());
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