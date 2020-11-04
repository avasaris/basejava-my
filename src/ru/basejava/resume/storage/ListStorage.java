package ru.basejava.resume.storage;

import ru.basejava.resume.exception.ExistStorageException;
import ru.basejava.resume.exception.NotExistStorageException;
import ru.basejava.resume.model.Resume;

import java.util.LinkedList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    final List<Resume> listStorage = new LinkedList<>();

    @Override
    public void save(Resume resume) {
        if (listStorage.contains(resume)) {
            throw new ExistStorageException(resume.getUuid());
        }
        listStorage.add(resume);
    }

    @Override
    public void update(Resume resume) {
        Resume resumeForUpdate = get(resume.getUuid());
        resumeForUpdate.setFullName(resume.getFullName());
    }

    @Override
    public Resume get(String uuid) {
        Resume searchResume = new Resume(uuid, "");
        if (!listStorage.contains(searchResume)) {
            throw new NotExistStorageException(uuid);
        }
        return listStorage.get(listStorage.indexOf(searchResume));
    }

    @Override
    public void delete(String uuid) {
        listStorage.remove(get(uuid));
    }

    @Override
    public void clear() {
        listStorage.clear();
    }

    @Override
    public Resume[] getAll() {
        return listStorage.toArray(Resume[]::new);
    }

    @Override
    public int size() {
        return listStorage.size();
    }
}
