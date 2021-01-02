package ru.basejava.resume.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrganisationSection extends Section {
    private static final long serialVersionUID = 1L;

    private final List<Organisation> organisations = new ArrayList<>();

    public OrganisationSection(Organisation ...organisations) {
        Collections.addAll(this.organisations, organisations);
    }

    public void add(Organisation organisation){
        organisations.add(organisation);
    }

    public List<Organisation> getOrganisations() {
        return organisations;
    }

    public void clear() {
        organisations.clear();
    }

    @Override
    public String toString() {
        return "OrganisationSection{" +
                "organisations=" + organisations +
                '}';
    }
}
