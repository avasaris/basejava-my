package ru.basejava.resume.storage;

import ru.basejava.resume.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int CAPACITY = 10000;
    Resume[] storage = new Resume[CAPACITY];
    private int count = 0;

    public void clear() {
        for (int i = 0; i < count; i++) {
            storage[i] = null;
        }
        count = 0;
    }

    public void save(Resume r) {
        if (count == CAPACITY) return;

        int index = getIndex(r.toString());
        if (index >= 0) {
            return;
        }

        storage[count] = r;
        count++;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            return;
        }

        for (int i = index; i < count - 1; i++) {
            storage[i] = storage[i + 1];
        }
        storage[count] = null;
        count--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[count];
        if (count >= 0) System.arraycopy(storage, 0, resumes, 0, count);
        return resumes;
    }

    public int size() {
        return count;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < count; i++) {
            if (storage[i].toString().equals(uuid)) return i;
        }
        return -1;
    }
}
