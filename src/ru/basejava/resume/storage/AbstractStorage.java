package ru.basejava.resume.storage;

import ru.basejava.resume.exception.ExistStorageException;
import ru.basejava.resume.exception.NotExistStorageException;
import ru.basejava.resume.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    public static final Comparator<Resume> RESUME_COMPARATOR =
            Comparator
                    .comparing(Resume::getFullName)
                    .thenComparing(Resume::getUuid);
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    static {
        LOG.setLevel(Level.SEVERE);
    }

    @Override
    public final void save(Resume resume) {
        LOG.info("Executed save(" + resume.getFullName() + ")");
        SK searchKey = elementNotExist(resume.getUuid());
        insertAt(searchKey, resume);
    }

    abstract void insertAt(SK searchKey, Resume resume);

    @Override
    public final void delete(String uuid) {
        LOG.info("Executed delete(" + uuid + ")");
        SK searchKey = elementExist(uuid);
        deleteAt(searchKey);
    }

    abstract void deleteAt(SK searchKey);

    @Override
    public final void update(Resume resume) {
        LOG.info("Executed update(" + resume.getFullName() + ")");
        SK searchKey = elementExist(resume.getUuid());
        updateAt(searchKey, resume);
    }

    abstract void updateAt(SK searchKey, Resume resume);

    @Override
    public final Resume get(String uuid) {
        LOG.info("Executed get(" + uuid + ")");
        SK searchKey = elementExist(uuid);
        return getAt(searchKey);
    }

    abstract Resume getAt(SK searchKey);

    private SK elementExist(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!checkKeyExist(searchKey)) {
            LOG.warning("elementExist(" + uuid + ") is FALSE");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    abstract boolean checkKeyExist(SK searchKey);

    abstract SK getSearchKey(String uuid);

    private SK elementNotExist(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (checkKeyExist(searchKey)) {
            LOG.warning("elementNotExist(" + uuid + ") is FALSE");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    @Override
    public final List<Resume> getAllSorted() {
        LOG.info("Executed getAllSorted()");
        List<Resume> storageCopy = getStorageAsList();
        storageCopy.sort(RESUME_COMPARATOR);
        return storageCopy;
    }

    abstract List<Resume> getStorageAsList();
}
