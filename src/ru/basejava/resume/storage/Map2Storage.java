package ru.basejava.resume.storage;

import ru.basejava.resume.model.Resume;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Map2Storage extends AbstractStorage {

    private final Map<String, Resume> mapStorage = new HashMap<>();

    @Override
    public void clear() {
        mapStorage.clear();
    }

    @Override
    public int size() {
        return mapStorage.size();
    }

    @Override
    Object getPointer(String uuid) {
        return uuid;
    }

    @Override
    boolean checkIndexExist(Object key) {
        return mapStorage.containsKey(key);
    }

    @Override
    void insertAt(Object key, Resume resume) {
        mapStorage.put((String) key, resume);
    }

    @Override
    void deleteAt(Object key) {
        mapStorage.remove(key);
    }

    @Override
    void updateAt(Object key, Resume resume) {
        mapStorage.replace((String) key, resume);
    }

    @Override
    Resume getAt(Object key) {
        return mapStorage.get(key);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> storageCopy = new LinkedList<>(mapStorage.values());
        storageCopy.sort(RESUME_COMPARATOR);
        return storageCopy;
    }
}
