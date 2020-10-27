package ru.basejava.resume.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.basejava.resume.exception.NotExistStorageException;
import ru.basejava.resume.model.Resume;

public abstract class AbstractArrayStorageTest {
    Storage storage;

    private final String UUID1 = "uuid1";
    private final String UUID2 = "uuid2";
    private final String UUID3 = "uuid3";

    @Before
    public void setStorage() {
        storage.clear();
        storage.save(new Resume(UUID1));
        storage.save(new Resume(UUID2));
        storage.save(new Resume(UUID3));
    }

    @Test
    public void get() {
        Assert.assertEquals(new Resume("uuid1"), storage.get("uuid1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void update() {
    }

    @Test
    public void save() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void clear() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void overflow() {

    }

}