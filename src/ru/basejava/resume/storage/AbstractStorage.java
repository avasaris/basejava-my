package ru.basejava.resume.storage;

import ru.basejava.resume.exception.ExistStorageException;
import ru.basejava.resume.exception.NotExistStorageException;
import ru.basejava.resume.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    public static final Comparator<Resume> RESUME_COMPARATOR =
            Comparator
                    .comparing(Resume::getFullName)
                    .thenComparing(Resume::getUuid);

    @Override
    public final void save(Resume resume) {
        SK searchKey = elementNotExist(resume.getUuid());
        insertAt(searchKey, resume);
    }

    abstract void insertAt(SK searchKey, Resume resume);

    @Override
    public final void delete(String uuid) {
        SK searchKey = elementExist(uuid);
        deleteAt(searchKey);
    }

    abstract void deleteAt(SK searchKey);

    @Override
    public final void update(Resume resume) {
        SK searchKey = elementExist(resume.getUuid());
        updateAt(searchKey, resume);
    }

    abstract void updateAt(SK searchKey, Resume resume);

    @Override
    public final Resume get(String uuid) {
        SK searchKey = elementExist(uuid);
        return getAt(searchKey);
    }

    abstract Resume getAt(SK searchKey);

    private SK elementExist(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!checkKeyExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    abstract boolean checkKeyExist(SK searchKey);

    abstract SK getSearchKey(String uuid);

    private SK elementNotExist(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (checkKeyExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    @Override
    public final List<Resume> getAllSorted() {
        List<Resume> storageCopy = getStorageAsList();
        storageCopy.sort(RESUME_COMPARATOR);
        return storageCopy;
    }

    abstract List<Resume> getStorageAsList();
}
