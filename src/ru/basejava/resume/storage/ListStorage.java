package ru.basejava.resume.storage;

import ru.basejava.resume.exception.ExistStorageException;
import ru.basejava.resume.exception.NotExistStorageException;
import ru.basejava.resume.exception.StorageException;
import ru.basejava.resume.model.Resume;

import java.util.LinkedList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    List<Resume> storage = new LinkedList<>();

    @Override
    public void save(Resume resume) {
        if (storage.contains(resume)) {
            throw new ExistStorageException(resume.getUuid());
        }
        if (storage.size() == CAPACITY) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        storage.add(resume);
    }

    @Override
    public void update(Resume resume) {
        Resume resumeForUpdate = get(resume.getUuid());
        resumeForUpdate.updateFrom(resume);
    }

    @Override
    public Resume get(String uuid) {
        Resume searchResume = new Resume(uuid);
        if (!storage.contains(searchResume)) {
            throw new NotExistStorageException(uuid);
        }
        return storage.get(storage.indexOf(searchResume));
    }

    @Override
    public void delete(String uuid) {
        storage.remove(get(uuid));
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(Resume[]::new);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
