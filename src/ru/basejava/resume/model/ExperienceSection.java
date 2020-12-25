package ru.basejava.resume.model;

import java.util.*;

public class ExperienceSection implements Section {
    private final Link link;
    private final List<Experience> experiences = new ArrayList<>();

    public ExperienceSection(Link link, Experience ...experiences) {
        Objects.requireNonNull(link, "Link to organisation shouldn't be null.");
        Objects.requireNonNull(experiences, "Experience or education shouldn't be null.");
        this.link = link;
        Collections.addAll(this.experiences, experiences);
    }

    public Link getLink() {
        return link;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    @Override
    public String toString() {
        return "ExperienceSection{" +
                "link=" + link +
                '}';
    }
}
