package ru.basejava.resume.storage;

import ru.basejava.resume.exception.ExistStorageException;
import ru.basejava.resume.exception.NotExistStorageException;
import ru.basejava.resume.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    public final void save(Resume resume) {
        int index = checkElementNotExist(resume.getUuid());
        insertAt(resume, index);
    }

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

    private int checkElementExist(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    abstract int getIndex(String uuid);

    private int checkElementNotExist(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }
}
