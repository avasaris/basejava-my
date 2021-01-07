package ru.basejava.resume.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ListSection extends Section<String> {
    private static final long serialVersionUID = 1L;

    private final List<String> items = new ArrayList<>();

    public ListSection() {
    }

    public ListSection(String... items) {
//        Objects.requireNonNull(items, "Items shouldn't be null");
        this.items.addAll(Arrays.asList(items));
    }

    @Override
    public void addItem(String item) {
        items.add(item);
    }

    @Override
    public String toString() {
        return "* " + String.join("\n* ", items);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public Stream<String> getItemsStream() {
        return items.stream();
    }
}