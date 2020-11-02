package ru.basejava.resume.storage;

import ru.basejava.resume.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid));
    }

    @Override
    protected void insertAt(Resume resume, int index) {
        int insPoint = -index - 1;
        System.arraycopy(storage, insPoint, storage, insPoint + 1, size - insPoint);
        storage[insPoint] = resume;
    }

    @Override
    protected void shiftAt(int index) {
        int newPoint = index + 1;
        System.arraycopy(storage, newPoint, storage, index, size - newPoint);
    }
}
