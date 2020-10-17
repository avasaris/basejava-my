package ru.basejava.resume.storage;

import ru.basejava.resume.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int CAPACITY = 10_000;

    final Resume[] storage = new Resume[CAPACITY];
    protected int size = 0;

    // Template Method 1 begin
    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("ERROR: Didn't found the resume '" + uuid + "'.");
            return null;
        }
        return storage[index];
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

    protected abstract int getIndex(String uuid);
    // Template Method 1 end

    // Template Method 2 begin
    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            System.out.println("ERROR: Already have the resume '" + resume + "'.");
            return;
        }

        if (size == CAPACITY) {
            System.out.println("ERROR: You reach the storage capacity.");
            return;
        }

        saveAt(resume, index);
        size++;
    }

    protected abstract void saveAt(Resume resume, int index);
    // Template Method 2 end

    // Template Method 3 begin
    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("ERROR: Didn't found the resume: '" + uuid + "'.");
            return;
        }

        deleteAt(index);
        size--;
    }

    protected abstract void deleteAt(int index);
    // Template Method 3 end

    // Common methods
    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public int size() {
        return size;
    }
}
