package ru.basejava.resume.storage;

import ru.basejava.resume.exception.NotExistStorageException;
import ru.basejava.resume.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    public final void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        checkForSaveExceptions(index, resume.getUuid());
        insertAt(resume, index);
    }

    abstract int getIndex(String uuid);
    abstract void checkForSaveExceptions(int index, String uuid);
    abstract void insertAt(Resume resume, int index);

    @Override
    public final void delete(String uuid) {
        int index = getIndex(uuid);
        checkElementExist(index, uuid);
        deleteAt(index);
    }

    abstract void deleteAt(int index);

    @Override
    public final void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        checkElementExist(index, resume.getUuid());
        updateAt(resume, index);
    }

    abstract void updateAt(Resume resume, int index);

    @Override
    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        checkElementExist(index, uuid);
        return getAt(index);
    }

    abstract Resume getAt(int index);

    @Override
    public final Resume[] getAll() {
        return storageCopy();
    }

    abstract Resume[] storageCopy();

    private void checkElementExist(int index, String uuid) {
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
    }
}
