package ru.basejava.resume.model;

import java.util.ArrayList;
import java.util.List;

public final class LinksList {
    private static final List<Link> links = new ArrayList<>();
    private static LinksList instance;

    private LinksList() {
    }

    public static Link get(Link link) {
        if (instance == null) {
            instance = new LinksList();
        }
        if (!links.contains(link)) {
            links.add(link);
        }
        return links.get(links.indexOf(link));
    }
}
