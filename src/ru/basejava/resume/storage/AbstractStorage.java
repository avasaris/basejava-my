package ru.basejava.resume.storage;

import ru.basejava.resume.exception.ExistStorageException;
import ru.basejava.resume.exception.NotExistStorageException;
import ru.basejava.resume.exception.StorageException;
import ru.basejava.resume.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    public final void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        if (checkCapacity()) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        insertAt(resume, index);
    }

    @Override
    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        deleteAt(index);
    }

    @Override
    public final void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        updateAt(resume, index);
    }

    @Override
    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getAt(index);
    }

    @Override
    public final Resume[] getAll() {
        return storageCopy();
    }

    abstract int getIndex(String uuid);

    abstract boolean checkCapacity();

    abstract void insertAt(Resume resume, int index);

    abstract void deleteAt(int index);

    abstract void updateAt(Resume resume, int index);

    abstract Resume getAt(int index);

    abstract Resume[] storageCopy();
}
