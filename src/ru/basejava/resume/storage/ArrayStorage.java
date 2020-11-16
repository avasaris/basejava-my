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

    //new
    Integer getPointer(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    void insertIntoStorage(int index, Resume resume) {
        storage[size] = resume;
    }

    @Override
    void deleteFromStorage(int index) {
        storage[index] = storage[size - 1];
    }
}
