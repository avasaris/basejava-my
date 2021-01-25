package ru.basejava.resume.storage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.basejava.resume.ResumeTestData;
import ru.basejava.resume.exception.ExistStorageException;
import ru.basejava.resume.exception.NotExistStorageException;
import ru.basejava.resume.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public abstract class AbstractStorageTest {
    protected static final String STORAGE_DIR = "storage";
    private static final String UUID1 = UUID.randomUUID().toString();
    private static final String UUID2 = UUID.randomUUID().toString();
    private static final String UUID3 = UUID.randomUUID().toString();
    private static final String UUID_NOT_EXIST = UUID.randomUUID().toString();
    private static final Comparator<Resume> RESUME_COMPARATOR = AbstractStorage.RESUME_COMPARATOR;
    final Resume resume1 = new Resume(UUID1, "Alex");
    final Resume resume2 = new Resume(UUID2, "Zed");
    {
        ResumeTestData.fillResume(resume2, "personal", "objective");
    }
    final Resume resume3 = new Resume(UUID3, "Dan");
    {
        ResumeTestData.fillResume(resume2, "personal", "objective", "section1");
    }
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

    @After
    public void finish() {
        storage.clear();
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
        Resume resumeForUpdate = new Resume(UUID1, "");
        ResumeTestData.fillResume(resumeForUpdate, "personal", "objective", "section2");
        storage.update(resumeForUpdate);
        Assert.assertEquals(resumeForUpdate, storage.get(UUID1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume(UUID_NOT_EXIST, "Liza Maria"));
    }

    @Test
    public void save() {
        Resume resumeForSave = new Resume(UUID_NOT_EXIST, "Karl");
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