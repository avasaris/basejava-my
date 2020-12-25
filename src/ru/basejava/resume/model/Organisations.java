package ru.basejava.resume.model;

import java.util.ArrayList;
import java.util.List;

public class Organisations {
    private static Organisations instance;
    private final List<Link> links = new ArrayList<>();

    private Organisations() {
    }

    public static Organisations getInstance() {
        if (instance == null) {
            instance = new Organisations();
        }
        return instance;
    }

    public Link get(String name, String url) {
        Link link = new Link(name, url);
        if (!links.contains(link)) {
            links.add(link);
        }
        return links.get(links.indexOf(link));
    }
}
