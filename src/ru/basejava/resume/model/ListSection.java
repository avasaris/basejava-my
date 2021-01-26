package ru.basejava.resume.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListSection extends Section {
    private static final long serialVersionUID = 1L;

    private final List<String> items = new ArrayList<>();

    public ListSection() {
    }

    public ListSection(String... items) {
        this.items.addAll(Arrays.asList(items));
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return String.join("\n", items);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ListSection that = (ListSection) o;

        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }

    public void add(String item) {
        items.add(item);
    }
}