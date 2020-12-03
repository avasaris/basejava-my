package ru.basejava.resume.storage;

import ru.basejava.resume.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage<Integer> {

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
    Integer getSearchKey(String uuid) {
        int index = -1;
        int counter = 0;
        for(Resume resume: listStorage){
            if(uuid.equals(resume.getUuid())) {
                index = counter;
                break;
            }
            counter++;
        }
        return index;
    }

    @Override
    boolean checkKeyExist(Integer index) {
        return index >= 0;
    }

    @Override
    void insertAt(Integer index, Resume resume) {
        listStorage.add(resume);
    }

    @Override
    void deleteAt(Integer index) {
        listStorage.remove(index.intValue());
    }

    @Override
    void updateAt(Integer index, Resume resume) {
        listStorage.set(index, resume);
    }

    @Override
    Resume getAt(Integer index) {
        return listStorage.get(index);
    }

    @Override
    public List<Resume> getStorageAsList() {
        return new LinkedList<>(listStorage);
    }
}
