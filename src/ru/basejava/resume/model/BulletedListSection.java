package ru.basejava.resume.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class BulletedListSection extends Section {
    private static final long serialVersionUID = 1L;

    private final List<String> value = new ArrayList<>();

    public BulletedListSection() {
    }

    public BulletedListSection(String... items) {
        Objects.requireNonNull(items, "Qualification or achievement shouldn't be null");
        this.value.addAll(Arrays.asList(items));
    }

    @Override
    public String toString() {
        return "* " + String.join("\n* ", value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BulletedListSection that = (BulletedListSection) o;

        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int size() {
        return value.size();
    }

    @Override
    public Stream getValue() {
        return value.stream();
    }
}