package ru.basejava.resume.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.basejava.resume.exception.ExistStorageException;
import ru.basejava.resume.exception.NotExistStorageException;
import ru.basejava.resume.exception.StorageException;
import ru.basejava.resume.model.Resume;

import java.util.Arrays;
import java.util.UUID;

public abstract class AbstractArrayStorageTest {
    private final String UUID1 = "uuid1";
    private final String UUID2 = "uuid2";
    private final String UUID3 = "uuid3";
    private final Resume resume1 = new Resume(UUID1);
    private final Resume resume2 = new Resume(UUID2);
    private final Resume resume3 = new Resume(UUID3);
    Storage storage;

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
        String newUuid = UUID.randomUUID().toString();
        Resume newResume = new Resume(newUuid);
        storage.save(newResume);
        Assert.assertEquals(storage.get(newUuid), newResume);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(resume1);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        while(storage.size() <= AbstractArrayStorage.CAPACITY){
            storage.save(new Resume());
        }
    }

    @Test
    public void delete() {
        storage.delete(UUID1);
        Assert.assertArrayEquals(Arrays.stream(storage.getAll()).sorted().toArray(), Arrays.stream(new Resume[]{resume2, resume3}).sorted().toArray());
        storage.delete(UUID3);
        Assert.assertArrayEquals(storage.getAll(), new Resume[]{resume2});
        storage.delete(UUID2);
        Assert.assertArrayEquals(storage.getAll(), new Resume[]{});
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        String newUuid = UUID.randomUUID().toString();
        storage.delete(newUuid);
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertArrayEquals(storage.getAll(), new Resume[]{});
    }

    @Test
    public void getAll() {
        Assert.assertArrayEquals(storage.getAll(), new Resume[]{resume1, resume2, resume3});
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }



}