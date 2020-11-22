package ru.basejava.resume.storage;

import ru.basejava.resume.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());

    @Override
    Object getPointer(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid, ""), RESUME_COMPARATOR);
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
