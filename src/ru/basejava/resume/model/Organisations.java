package ru.basejava.resume.model;

import java.util.HashMap;
import java.util.Map;

public class Organisations {
    private static Organisations instance;
    private final Map<Integer, Organisation> storage = new HashMap<>();

    private Organisations() {
    }

    public static Organisations getInstance() {
        if (instance == null) {
            instance = new Organisations();
        }
        return instance;
    }

    public Organisation get(String name, String url){
        Organisation organisation = new Organisation(name, url);
        int key = organisation.hashCode();
        storage.putIfAbsent(key, organisation);
        return storage.get(key);
    }

    @Override
    public String toString() {
        return "Organisations{" +
                "storage=" + storage +
                '}';
    }
}
