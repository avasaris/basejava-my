package ru.basejava.resume.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExperienceSection implements Section {
    private final List<Experience> storage = new ArrayList<>();

    public ExperienceSection(Experience ...experiences) {
        Collections.addAll(this.storage, experiences);
    }

    @Override
    public String toString() {
        StringBuilder experienceString = new StringBuilder();
        for(Experience element:storage){
            experienceString.append(element.toString());
            experienceString.append("\n");
        }
        return experienceString.toString();
    }
}
