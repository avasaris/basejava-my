package ru.basejava.resume.storage;

import ru.basejava.resume.model.Resume;

public interface Storage {
    void save(Resume resume);

    void update(Resume resume);

    void delete(String uuid);

    Resume get(String uuid);

    Resume[] getAll();

    void clear();

    int size();
}
