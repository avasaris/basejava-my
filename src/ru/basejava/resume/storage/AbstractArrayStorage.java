package ru.basejava.resume.storage;

import ru.basejava.resume.exception.StorageException;
import ru.basejava.resume.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    static final int CAPACITY = 10_000;
    final Resume[] storage = new Resume[CAPACITY];
    int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    final void insertAt(int index, Resume resume) {
        if (size == CAPACITY) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        storageInsert(index, resume);
        size++;
    }

    abstract void storageInsert(int index, Resume resume);

    @Override
    final void deleteAt(int index) {
        storageDelete(index);
        storage[size - 1] = null;
        size--;
    }

    abstract void storageDelete(int index);

    @Override
    void updateAt(int index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    Resume getAt(int index) {
        return storage[index];
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }
}
