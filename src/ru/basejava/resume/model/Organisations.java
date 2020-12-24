package ru.basejava.resume.model;

import java.util.HashMap;
import java.util.Map;

public class Organisations {
    private static Organisations instance;
    private final Map<Integer, Link> storage = new HashMap<>();

    private Organisations() {
    }

    public static Organisations getInstance() {
        if (instance == null) {
            instance = new Organisations();
        }
        return instance;
    }

    public Link get(String name, String url){
        Link link = new Link(name, url);
        int key = link.hashCode();
        storage.putIfAbsent(key, link);
        return storage.get(key);
    }

    @Override
    public String toString() {
        return "Organisations{" +
                "storage=" + storage +
                '}';
    }
}
