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
        return mapResumeStorage.getOrDefault(uuid, new Resume(uuid,""));
    }

    @Override
    boolean checkKeyExist(Resume resume) {
        return mapResumeStorage.containsValue(resume);
    }

    @Override
    void insertAt(Resume resume, Resume newResume) {
        mapResumeStorage.put(resume.getUuid(), newResume);
    }

    @Override
    void deleteAt(Resume resume) {
       mapResumeStorage.remove(resume.getUuid());
    }

    @Override
    void updateAt(Resume resume, Resume newResume) {
        mapResumeStorage.replace(resume.getUuid(), newResume);
    }

    @Override
    Resume getAt(Resume resume) {
        return resume;
    }

    @Override
    public List<Resume> getStorageAsList() {
        return new LinkedList<>(mapResumeStorage.values());
    }
}
