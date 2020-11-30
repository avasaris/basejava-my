package ru.basejava.resume.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public  class ListStringSection implements AbstractSection {
    private final List<String> value = new ArrayList<>();

    public ListStringSection(String... strings) {
        this.value.addAll(Arrays.asList(strings));
    }

    @Override
    public String toString() {
        return "* " + String.join("\n\t* ", value);
    }
}