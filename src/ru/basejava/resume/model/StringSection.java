package ru.basejava.resume.model;

public class StringSection implements AbstractSection {
    private final String value;

    public StringSection(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
