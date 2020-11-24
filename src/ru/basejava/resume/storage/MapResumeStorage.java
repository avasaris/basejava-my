package ru.basejava.resume.storage;

import ru.basejava.resume.model.Resume;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {

    private final Map<String, Resume> mapResumeStorage = new HashMap<>();

    @Override
    public void clear() {
        mapResumeStorage.clear();
    }

    @Override
    public int size() {
        return mapResumeStorage.size();
    }

    @Override
    Resume getSearchKey(String uuid) {
        Resume resume = mapResumeStorage.get(uuid);
        return resume != null ? resume : new Resume(uuid, "");
    }

    @Override
    boolean checkKeyExist(Object resume) {
        return mapResumeStorage.containsValue(resume);
    }

    @Override
    void insertAt(Object resume, Resume newResume) {
        String uuid = ((Resume) resume).getUuid();
        mapResumeStorage.put(uuid, newResume);
        mapResumeStorage.
    }

    @Override
    void deleteAt(Object resume) {
        String uuid = ((Resume) resume).getUuid();
        mapResumeStorage.remove(uuid);
    }

    @Override
    void updateAt(Object resume, Resume newResume) {
        String uuid = ((Resume) resume).getUuid();
        mapResumeStorage.replace(uuid, newResume);
    }

    @Override
    Resume getAt(Object resume) {
        return (Resume) resume;
    }

    @Override
    public List<Resume> getStorageAsList() {
        return new LinkedList<>(mapResumeStorage.values());
    }
}
