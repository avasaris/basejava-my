package ru.basejava.resume.storage;

import ru.basejava.resume.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    Object getPointer(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid, ""));
    }

    @Override
    void insertIntoStorage(int index, Resume resume) {
        int insPosition = -index - 1;
        System.arraycopy(storage, insPosition, storage, insPosition + 1, size - insPosition);
        storage[insPosition] = resume;
    }

    @Override
    void deleteFromStorage(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - (index + 1));
    }
}
