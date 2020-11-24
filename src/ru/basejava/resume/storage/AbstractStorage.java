package ru.basejava.resume.storage;

import ru.basejava.resume.exception.ExistStorageException;
import ru.basejava.resume.exception.NotExistStorageException;
import ru.basejava.resume.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    public static final Comparator<Resume> RESUME_COMPARATOR =
            Comparator
                    .comparing(Resume::getFullName)
                    .thenComparing(Resume::getUuid);

    @Override
    public final void save(Resume resume) {
        Object searchKey = elementNotExist(resume.getUuid());
        insertAt(searchKey, resume);
    }

    abstract void insertAt(Object searchKey, Resume resume);

    @Override
    public final void delete(String uuid) {
        Object searchKey = elementExist(uuid);
        deleteAt(searchKey);
    }

    abstract void deleteAt(Object searchKey);

    @Override
    public final void update(Resume resume) {
        Object searchKey = elementExist(resume.getUuid());
        updateAt(searchKey, resume);
    }

    abstract void updateAt(Object searchKey, Resume resume);

    @Override
    public final Resume get(String uuid) {
        Object searchKey = elementExist(uuid);
        return getAt(searchKey);
    }

    abstract Resume getAt(Object searchKey);

    private Object elementExist(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!checkKeyExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    abstract boolean checkKeyExist(Object searchKey);

    abstract Object getSearchKey(String uuid);

    private Object elementNotExist(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (checkKeyExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey != null ? searchKey : new Resume(uuid, "");
    }

    @Override
    public final List<Resume> getAllSorted() {
        List<Resume> storageCopy = getStorageAsList();
        storageCopy.sort(RESUME_COMPARATOR);
        return storageCopy;
    }

    abstract List<Resume> getStorageAsList();
}
