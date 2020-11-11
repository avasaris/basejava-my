package ru.basejava.resume.storage;

import ru.basejava.resume.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    int getIndex(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid, ""));
    }

    @Override
    int calculateInsertPosition(int index) {
        if (index < 0) {
            index = -index - 1;
        }
        return index;
    }

    @Override
    void prepareStorageForInsert(int insPoint) {
        System.arraycopy(storage, insPoint, storage, insPoint + 1, size - insPoint);
    }

    @Override
    void shiftAt(int index) {
        int newPoint = index + 1;
        System.arraycopy(storage, newPoint, storage, index, size - newPoint);
    }
}
