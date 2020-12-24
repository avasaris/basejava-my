package ru.basejava.resume.storage;

import ru.basejava.resume.model.Resume;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {

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
    String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    boolean checkKeyExist(String key) {
        return mapUuidStorage.containsKey(key);
    }

    @Override
    void insertAt(String key, Resume resume) {
        mapUuidStorage.put(key, resume);
    }

    @Override
    void deleteAt(String key) {
        mapUuidStorage.remove(key);
    }

    @Override
    void updateAt(String key, Resume resume) {
        mapUuidStorage.replace(key, resume);
    }

    @Override
    Resume getAt(String key) {
        return mapUuidStorage.get(key);
    }

    @Override
    public List<Resume> getStorageAsList() {
        return new LinkedList<>(mapUuidStorage.values());
    }
}
