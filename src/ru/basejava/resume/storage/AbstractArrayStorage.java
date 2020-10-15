package ru.basejava.resume.storage;

import ru.basejava.resume.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int CAPACITY = 10_000;
    protected int size = 0;
    final Resume[] storage = new Resume[CAPACITY];

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("ERROR: Didn't found the resume '" + uuid + "'.");
            return null;
        }
        return storage[index];
    }

    abstract protected int getIndex(String uuid);
}
