package ru.basejava.resume.storage;

import ru.basejava.resume.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    int getPosition(String fullName) {
        return 0;
    }

    @Override
    void insertAt(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    void shiftAt(int index) {
        storage[index] = storage[size - 1];
    }
}
