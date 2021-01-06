package ru.basejava.resume.model;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class SingleLineSection extends Section {
    private static final long serialVersionUID = 1L;

    private String value;

    public SingleLineSection() {
    }

    public SingleLineSection(String value) {
        Objects.requireNonNull(value, "Objective or personal data shouldn't be null");
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleLineSection that = (SingleLineSection) o;

        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public Stream getValue() {
        return Arrays.asList(value).stream();
    }
}
