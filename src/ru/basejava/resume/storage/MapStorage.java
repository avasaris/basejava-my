//package ru.basejava.resume.storage;
//
//import ru.basejava.resume.model.Resume;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class MapStorage extends AbstractStorage {
//
//    private final Map<String, Resume> mapStorage = new HashMap<>();
//
//    @Override
//    public void clear() {
//        mapStorage.clear();
//    }
//
//    @Override
//    public int size() {
//        return mapStorage.size();
//    }
//
//    @Override
//    void insertAt(int index, Resume resume) {
//        mapStorage.put(resume.getUuid(), resume);
//    }
//
//    @Override
//    void deleteAt(int index) {
//
//    }
//
//    @Override
//    void updateAt(int index, Resume resume) {
//
//    }
//
//    @Override
//    Resume getAt(int index) {
//        return null;
//    }
//
//    @Override
//    int getIndex(String uuid) {
//
//        return 0;
//    }
//
//    @Override
//    public Resume[] getAll() {
//        return new Resume[0];
//    }
//}
