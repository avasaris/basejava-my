package ru.basejava.resume.storage;

import ru.basejava.resume.model.Resume;

import java.util.List;

public interface Storage {
    void save(Resume resume);

    void update(Resume resume);

    void delete(String uuid);

    Resume get(String uuid);

    List<Resume> getAllSorted();

    void clear();

    int size();
}
