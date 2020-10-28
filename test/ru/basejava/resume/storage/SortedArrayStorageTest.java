package ru.basejava.resume.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.basejava.resume.model.Resume;

import java.util.Arrays;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }

    @Test
    public void getAllSorted() {
        Object[] expected = Arrays.stream(new Resume[]{resume1, resume2, resume3}).sorted().toArray();
        Object[] actual = Arrays.stream(storage.getAll()).toArray();
        Assert.assertArrayEquals(expected, actual);
    }
}
