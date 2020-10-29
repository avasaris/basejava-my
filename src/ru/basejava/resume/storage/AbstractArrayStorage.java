package ru.basejava.resume.storage;

import ru.basejava.resume.exception.ExistStorageException;
import ru.basejava.resume.exception.NotExistStorageException;
import ru.basejava.resume.exception.StorageException;
import ru.basejava.resume.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    static final int CAPACITY = 10_000;

    final Resume[] storage = new Resume[CAPACITY];
    int size = 0;

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        storage[index] = resume;
    }

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        if (size == CAPACITY) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        insertAt(resume, index);
        size++;
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        shiftAt(index);
        storage[size - 1] = null;
        size--;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insertAt(Resume resume, int index);

    protected abstract void shiftAt(int index);


    // Common methods
    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int size() {
        return size;
    }
}
