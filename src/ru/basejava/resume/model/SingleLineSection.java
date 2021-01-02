package ru.basejava.resume.model;

import java.util.Objects;

public class SingleLineSection extends Section {
    private static final long serialVersionUID = 1L;

    private final String value;

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
}
