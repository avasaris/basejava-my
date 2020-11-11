package ru.basejava.resume.storage;

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
    int calculateInsertPosition(int index) {
        return size;
    }

    @Override
    void prepareStorageForInsert(int insPoint) {
    }

    @Override
    void shiftAt(int index) {
        storage[index] = storage[size - 1];
    }
}
