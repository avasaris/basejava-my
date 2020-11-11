package ru.basejava.resume.storage;

import ru.basejava.resume.exception.ExistStorageException;
import ru.basejava.resume.exception.NotExistStorageException;
import ru.basejava.resume.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    public final void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        checkAbilityForSave(index, resume.getUuid());
        insertAt(resume, index);
    }

    abstract void checkAbilityForSave(int index, String uuid);
    abstract void insertAt(Resume resume, int index);

    @Override
    public final void delete(String uuid) {
        int index = checkElementExist(uuid);
        deleteAt(index);
    }

    abstract void deleteAt(int index);

    @Override
    public final void update(Resume resume) {
        int index = checkElementExist(resume.getUuid());
        updateAt(resume, index);
    }

    abstract void updateAt(Resume resume, int index);

    @Override
    public final Resume get(String uuid) {
        int index = checkElementExist(uuid);
        return getAt(index);
    }

    abstract Resume getAt(int index);

    @Override
    public final Resume[] getAll() {
        return storageCopy();
    }

    abstract Resume[] storageCopy();

    private int checkElementExist(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    abstract int getIndex(String uuid);

    void checkElementNotExist(int index, String uuid) {
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        }
    }
}
