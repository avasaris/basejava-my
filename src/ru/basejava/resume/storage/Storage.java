package ru.basejava.resume.storage;

import ru.basejava.resume.model.Resume;

public interface Storage {
    void clear();

    void save(Resume resume);

    void update(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);

    Resume[] getAll();

    int size();
}
