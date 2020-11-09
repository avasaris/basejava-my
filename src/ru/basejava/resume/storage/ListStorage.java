package ru.basejava.resume.storage;

import ru.basejava.resume.model.Resume;

import java.util.LinkedList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    final List<Resume> listStorage = new LinkedList<>();

    @Override
    void deleteAt(int index) {
        listStorage.remove(index);
    }

    @Override
    boolean checkCapacity() {
        return false;
    }

    @Override
    int getIndex(String uuid) {
        return listStorage.indexOf(new Resume(uuid, ""));
    }

    @Override
    void insertAt(Resume resume, int index) {
        listStorage.add(resume);
    }

    @Override
    void increaseSize() {
    }

    @Override
    void decreaseSize() {
    }

    @Override
    void updateAt(Resume resume, int index) {
        listStorage.set(index, resume);
    }

    @Override
    Resume getAt(int index) {
        return listStorage.get(index);
    }

    @Override
    public void clear() {
        listStorage.clear();
    }

    @Override
    public Resume[] getAll() {
        return listStorage.toArray(Resume[]::new);
    }

    @Override
    public int size() {
        return listStorage.size();
    }
}
