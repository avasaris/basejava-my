package ru.basejava.resume.storage;

import ru.basejava.resume.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int CAPACITY = 10_000;
    protected int size = 0;
    Resume[] storage = new Resume[CAPACITY];

    public abstract void save(Resume resume);

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
