package ru.basejava.resume.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public  class BulletedListSection implements Section {
    private final List<String> value = new ArrayList<>();

    public BulletedListSection(String... strings) {
        Objects.requireNonNull(strings, "Qualification or achievement shouldn't be null");
        this.value.addAll(Arrays.asList(strings));
    }

    @Override
    public String toString() {
        return "* " + String.join("\n\t* ", value);
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
}