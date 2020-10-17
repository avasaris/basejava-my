package ru.basejava.resume.storage;

import ru.basejava.resume.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    // Implement step from Template Method 1 from AbstractArrayStorage
    @Override
    protected int getIndex(String uuid) {
        Resume searchResume = new Resume();
        searchResume.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchResume);
    }

    // Implement step from Template Method 2 from AbstractArrayStorage
    @Override
    protected void saveAt(Resume resume, int index) {
        System.arraycopy(storage, -index - 1, storage, -index, size + index + 1);
        storage[-index - 1] = resume;
    }

    // Implement step from Template Method 3 from AbstractArrayStorage
    @Override
    protected void deleteAt(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
        storage[size - 1] = null;
    }
}
