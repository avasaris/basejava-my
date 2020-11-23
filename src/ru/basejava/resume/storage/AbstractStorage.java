package ru.basejava.resume.storage;

import ru.basejava.resume.exception.ExistStorageException;
import ru.basejava.resume.exception.NotExistStorageException;
import ru.basejava.resume.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    public static final Comparator<Resume> RESUME_COMPARATOR = (Resume o1, Resume o2) -> {
        int compareResult = o1.getFullName().compareTo(o2.getFullName());
        if (compareResult == 0)
            return o1.getUuid().compareTo(o2.getUuid());
        else
            return compareResult;
    };

    @Override
    public final void save(Resume resume) {
        Object index = elementNotExist(resume.getUuid());
        insertAt(index, resume);
    }

    abstract void insertAt(Object index, Resume resume);

    @Override
    public final void delete(String uuid) {
        Object index = elementExist(uuid);
        deleteAt(index);
    }

    abstract void deleteAt(Object index);

    @Override
    public final void update(Resume resume) {
        Object index = elementExist(resume.getUuid());
        updateAt(index, resume);
    }

    abstract void updateAt(Object index, Resume resume);

    @Override
    public final Resume get(String uuid) {
        Object index = elementExist(uuid);
        return getAt(index);
    }

    abstract Resume getAt(Object index);

    private Object elementExist(String uuid) {
        Object index = getSearchKey(uuid);
        if (!checkIndexExist(index)) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    abstract boolean checkIndexExist(Object index);

    abstract Object getSearchKey(String uuid);

    private Object elementNotExist(String uuid) {
        Object index = getSearchKey(uuid);
        if (checkIndexExist(index)) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    @Override
    public final List<Resume> getAllSorted() {
        List<Resume> storageCopy = getStorageAsList();
        storageCopy.sort(RESUME_COMPARATOR);
        return storageCopy;
    }

    abstract List<Resume> getStorageAsList();
}
