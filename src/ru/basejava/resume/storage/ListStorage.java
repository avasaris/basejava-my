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
    int getIndex(String uuid) {
        return listStorage.indexOf(new Resume(uuid, ""));
    }

    @Override
    boolean checkCapacity() {
        return false;
    }

    @Override
    void insertAt(Resume resume, int index) {
        listStorage.add(resume);
    }

    @Override
    void deleteAt(int index) {
        listStorage.remove(index);
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
    public Resume[] storageCopy() {
        return listStorage.toArray(new Resume[0]);
    }
}
