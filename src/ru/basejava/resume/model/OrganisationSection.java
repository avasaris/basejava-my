package ru.basejava.resume.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrganisationSection extends Section {
    private static final long serialVersionUID = 1L;

    private final List<Organisation> organisations = new ArrayList<>();

    public OrganisationSection() {
    }

    public OrganisationSection(Organisation... organisations) {
        Collections.addAll(this.organisations, organisations);
    }

    public void add(Organisation organisation) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganisationSection that = (OrganisationSection) o;

        return organisations.equals(that.organisations);
    }

    @Override
    public int hashCode() {
        return organisations.hashCode();
    }

    @Override
    public int size() {
        return organisations.size();
    }

    @Override
    public Stream getValue() {
        return organisations.stream();
    }
}
