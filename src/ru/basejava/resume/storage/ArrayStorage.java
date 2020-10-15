package ru.basejava.resume.storage;

import ru.basejava.resume.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void save(Resume resume) {
        if (size == CAPACITY) {
            System.out.println("ERROR: You reach the storage capacity.");
            return;
        }

        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            System.out.println("ERROR: Already have the resume '" + resume + "'.");
            return;
        }

        storage[size] = resume;
        size++;
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            System.out.println("ERROR: Didn't found the resume '" + resume + "' for update.");
            return;
        }
        storage[index] = resume;
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("ERROR: Didn't found the resume: '" + uuid + "'.");
            return;
        }

        storage[index] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

}
