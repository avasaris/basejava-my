package ru.basejava.resume.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ExperienceSection implements Section {
    private final List<Experience> experiences = new ArrayList<>();

    public ExperienceSection(Experience ...experiences) {
        Objects.requireNonNull(experiences, "Experience or education shouldn't be null.");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExperienceSection that = (ExperienceSection) o;

        return experiences.equals(that.experiences);
    }

    @Override
    public int hashCode() {
        return experiences.hashCode();
    }
}
