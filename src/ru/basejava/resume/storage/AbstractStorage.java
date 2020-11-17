package ru.basejava.resume.storage;

import ru.basejava.resume.exception.ExistStorageException;
import ru.basejava.resume.exception.NotExistStorageException;
import ru.basejava.resume.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    public final void save(Resume resume) {
        int index = (int) checkElementNotExist(resume.getUuid());
        insertAt(index, resume);
    }

    abstract void insertAt(int index, Resume resume);

    @Override
    public final void delete(String uuid) {
        Object index = checkElementExist(uuid);
        deleteAt(index);
    }

    abstract void deleteAt(Object index);

    @Override
    public final void update(Resume resume) {
        Object index = checkElementExist(resume.getUuid());
        updateAt(index, resume);
    }

    abstract void updateAt(Object index, Resume resume);

    @Override
    public final Resume get(String uuid) {
        Object index = checkElementExist(uuid);
        return getAt(index);
    }

    abstract Resume getAt(Object index);

    private Object checkElementExist(String uuid) {
        Object index = getPointer(uuid);
        if (checkIndexNotExist(index)) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    abstract boolean checkIndexNotExist(Object index);
    abstract Object getPointer(String uuid);

    private Object checkElementNotExist(String uuid) {
        Object index = getPointer(uuid);
        if (!checkIndexNotExist(index)) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }
}
