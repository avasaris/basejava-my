package ru.basejava.resume.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public  class BulletedListSection implements Section {
    private final List<String> value = new ArrayList<>();

    public BulletedListSection(String... strings) {
        this.value.addAll(Arrays.asList(strings));
    }

    @Override
    public String toString() {
        return "* " + String.join("\n\t* ", value);
    }
}