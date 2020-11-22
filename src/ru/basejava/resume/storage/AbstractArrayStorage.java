package ru.basejava.resume.storage;

import ru.basejava.resume.exception.StorageException;
import ru.basejava.resume.model.Resume;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
    boolean checkIndexExist(Object index) {
        return (int) index >= 0;
    }

    @Override
    final void insertAt(Object index, Resume resume) {
        if (size == CAPACITY) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        insertIntoStorage((int)  index, resume);
        size++;
    }

    abstract void insertIntoStorage(int index, Resume resume);

    @Override
    final void deleteAt(Object index) {
        deleteFromStorage((int) index);
        storage[size - 1] = null;
        size--;
    }

    abstract void deleteFromStorage(int index);

    @Override
    void updateAt(Object index, Resume resume) {
        storage[(int) index] = resume;
    }

    @Override
    Resume getAt(Object index) {
        return storage[(int) index];
    }

    @Override
    public List<Resume> getStorageAsList() {
        return new LinkedList<>(Arrays.asList(Arrays.copyOf(storage, size)));
    }
}
