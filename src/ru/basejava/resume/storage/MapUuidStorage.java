package ru.basejava.resume.storage;

import ru.basejava.resume.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {

    private final Map<String, Resume> mapUuidStorage = new HashMap<>();

    @Override
    public void clear() {
        mapUuidStorage.clear();
    }

    @Override
    public int size() {
        return mapUuidStorage.size();
    }

    @Override
    Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    boolean checkKeyExist(Object key) {
        return mapUuidStorage.containsKey(key);
    }

    @Override
    void insertAt(Object key, Resume resume) {
        mapUuidStorage.put((String) key, resume);
    }

    @Override
    void deleteAt(Object key) {
        mapUuidStorage.remove(key);
    }

    @Override
    void updateAt(Object key, Resume resume) {
        mapUuidStorage.replace((String) key, resume);
    }

    @Override
    Resume getAt(Object key) {
        return mapUuidStorage.get(key);
    }

    @Override
    public List<Resume> getStorageAsList() {
        return new LinkedList<>(mapUuidStorage.values());
    }
}
