package ru.basejava.resume.model;

public class SingleLineSection implements Section {
    private final String value;

    public SingleLineSection(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
