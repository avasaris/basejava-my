package ru.basejava.resume.storage;

import ru.basejava.resume.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    static final int CAPACITY = 10_000;
    final Resume[] storage = new Resume[CAPACITY];
    int size = 0;

    abstract void shiftAt(int index);

    @Override
    Resume getAt(int index) {
        return storage[index];
    }

    @Override
    void deleteAt(int index) {
        shiftAt(index);
        storage[size - 1] = null;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    void increaseSize() {
        size++;
    }

    @Override
    void decreaseSize() {
        size--;
    }

    @Override
    boolean checkCapacity() {
        return size == CAPACITY;
    }

    @Override
    void updateAt(Resume resume, int index) {
        storage[index] = resume;
    }
}
