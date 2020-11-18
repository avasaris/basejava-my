package ru.basejava.resume.storage;

import ru.basejava.resume.model.Resume;

import java.util.LinkedList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private final List<Resume> listStorage = new LinkedList<>();

    @Override
    public void clear() {
        listStorage.clear();
    }

    @Override
    public int size() {
        return listStorage.size();
    }

    @Override
    Object getPointer(String uuid) {
        return listStorage.indexOf(new Resume(uuid, ""));
    }

    @Override
    boolean checkIndexExist(Object index) {
        return (int) index >= 0;
    }

    @Override
    void insertAt(Object index, Resume resume) {
        listStorage.add(resume);
    }

    @Override
    void deleteAt(Object index) {
        listStorage.remove((int) index);
    }

    @Override
    void updateAt(Object index, Resume resume) {
        listStorage.set((int) index, resume);
    }

    @Override
    Resume getAt(Object index) {
        return listStorage.get((int) index);
    }

    @Override
    public Resume[] getAll() {
        return listStorage.toArray(new Resume[0]);
    }
}
