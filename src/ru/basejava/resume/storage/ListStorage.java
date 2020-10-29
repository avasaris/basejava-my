package ru.basejava.resume.storage;

import ru.basejava.resume.exception.NotExistStorageException;
import ru.basejava.resume.model.Resume;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ListStorage extends AbstractStorage {

    List<Resume> storage = new LinkedList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void save(Resume resume) {
        storage.add(resume);
    }

    @Override
    public void update(Resume resume) {

    }

    @Override
    public Resume get(String uuid) {
        for (Resume resume : storage) {
            if (Objects.equals(resume.getUuid(), uuid)) {
                return resume;
            }
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        storage.remove(get(uuid));
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(Resume[]::new);
    }

    @Override
    public int size() {
        return 0;
    }
}
