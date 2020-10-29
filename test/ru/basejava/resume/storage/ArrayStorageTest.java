package ru.basejava.resume.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.basejava.resume.model.Resume;

import java.util.Arrays;

public class ArrayStorageTest extends AbstractArrayStorageTest {
    public ArrayStorageTest() {
        super(new ArrayStorage());
    }

    @Test
    public void getAll() {
        Resume[] expected = Arrays.stream(new Resume[]{resume1, resume2, resume3}).sorted().toArray(Resume[]::new);
        Resume[] actual = Arrays.stream(storage.getAll()).sorted().toArray(Resume[]::new);
        Assert.assertArrayEquals(expected, actual);
    }
}
