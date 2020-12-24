package ru.basejava.resume.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.basejava.resume.exception.ExistStorageException;
import ru.basejava.resume.exception.NotExistStorageException;
import ru.basejava.resume.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static ru.basejava.resume.ResumeTestData.generateRandomResume;

public abstract class AbstractStorageTest {
    private static final String UUID1 = UUID.randomUUID().toString();
    private static final String UUID2 = UUID.randomUUID().toString();
    private static final String UUID3 = UUID.randomUUID().toString();
    private static final String UUID_NOT_EXIST = UUID.randomUUID().toString();
    private static final Comparator<Resume> RESUME_COMPARATOR = AbstractStorage.RESUME_COMPARATOR;

    final Resume resume1 = generateRandomResume(UUID1, "Alex");
    final Resume resume2 = generateRandomResume(UUID2, "Zed");
    final Resume resume3 = generateRandomResume(UUID3, "Dan");
    final Storage storage;

    public AbstractStorageTest(Storage storage) {
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
        storage.get(UUID_NOT_EXIST);
    }

    @Test
    public void update() {
        Resume resumeForUpdate = generateRandomResume(UUID1, "");
        storage.update(resumeForUpdate);
        Assert.assertEquals(resumeForUpdate, storage.get(UUID1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume(UUID_NOT_EXIST,"Liza Maria"));
    }

    @Test
    public void save() {
        Resume resumeForSave = generateRandomResume(UUID_NOT_EXIST, "Karl");
        storage.save(resumeForSave);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(resumeForSave, storage.get(UUID_NOT_EXIST));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(resume1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID2);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_NOT_EXIST);
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(storage.size(), 0);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void getAllSorted() {
        List<Resume> expected = Arrays.asList(resume1, resume2, resume3);
        expected.sort(RESUME_COMPARATOR);
        List<Resume> actual = storage.getAllSorted();
        Assert.assertEquals(expected, actual);
    }
}