package ru.basejava.resume.storage;

import ru.basejava.resume.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    int getIndex(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid, ""));
    }

    @Override
    void storageInsert(int index, Resume resume) {
        int position = index < 0 ? -index - 1 : index;
        System.arraycopy(storage, position, storage, position + 1, size - position);
        storage[position] = resume;
    }

    @Override
    void storageDelete(int index) {
        int position = index + 1;
        System.arraycopy(storage, position, storage, index, size - position);
    }
}
