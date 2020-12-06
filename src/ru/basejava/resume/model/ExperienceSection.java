package ru.basejava.resume.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExperienceSection implements Section {
    private final List<Experience> experiences = new ArrayList<>();

    public ExperienceSection(Experience ...experiences) {
        Collections.addAll(this.experiences, experiences);
    }

    @Override
    public String toString() {
        StringBuilder experienceString = new StringBuilder();
        for(Experience element: experiences){
            experienceString.append(element.toString());
            experienceString.append("\n");
        }
        return experienceString.toString();
    }
}
