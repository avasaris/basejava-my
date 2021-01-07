package ru.basejava.resume.model;

import java.util.Objects;
import java.util.stream.Stream;

public class TextSection extends Section<String> {
    private static final long serialVersionUID = 1L;

    private String content;

    public TextSection() {
    }

    public TextSection(String content) {
        Objects.requireNonNull(content, "Content data shouldn't be null");
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;

        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public Stream<String> getItemsStream() {
        return Stream.of(content);
    }
}
