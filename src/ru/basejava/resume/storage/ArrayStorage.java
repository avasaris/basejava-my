package ru.basejava.resume.storage;

import ru.basejava.resume.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    Integer getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
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
