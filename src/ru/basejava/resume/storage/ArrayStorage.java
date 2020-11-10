package ru.basejava.resume.storage;

import ru.basejava.resume.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    void insertAt(Resume resume, int index) {
        storage[size] = resume;
        resize(1);
    }

    @Override
    void shiftAt(int index) {
        storage[index] = storage[size - 1];
    }
}
