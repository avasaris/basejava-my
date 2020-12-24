package ru.basejava.resume.model;

import java.util.*;

public class ExperienceSection implements Section {
    private final Map<Link, List<Experience>> experiences = new HashMap<>();

    public ExperienceSection(Experience ...experiences) {
        Objects.requireNonNull(experiences, "Experience or education shouldn't be null.");
        for (Experience experience : experiences) {
            if(this.experiences.containsKey(experience.getLink())){
                List<Experience> list = this.experiences.get(experience.getLink());
                list.add(experience);
                this.experiences.put(experience.getLink(), list);
            } else {
                List<Experience> list = new ArrayList<>();
                list.add(experience);
                this.experiences.put(experience.getLink(), list);
            }
        }
    }

    public Map<Link, List<Experience>> getExperiences() {
        return experiences;
    }
}
