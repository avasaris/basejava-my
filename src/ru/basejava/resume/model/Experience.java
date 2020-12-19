package ru.basejava.resume.model;

import java.time.YearMonth;
import java.util.Objects;

public class Experience {
    private final Organisation organisation;
    private final YearMonth begin;
    private final YearMonth end;
    private final String header;
    private final String description;

    public Experience(Organisation organisation, YearMonth begin, YearMonth end, String header, String description) {
        Objects.requireNonNull(begin, "Experience.begin sould't be null");
        Objects.requireNonNull(end, "Experience.end sould't be null");
        Objects.requireNonNull(header, "Experience.header sould't be null");
        this.organisation = organisation;
        this.begin = begin;
        this.end = end;
        this.header = header;
        this.description = description;
    }

    @Override
    public String toString() {
        return "ExtendedSection{" +
                "urlName='" + organisation.getName() + '\'' +
                ", url='" + organisation.getUrl() + '\'' +
                ", begin=" + begin +
                ", end=" + end +
                ", header='" + header + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Experience that = (Experience) o;

        if (!organisation.equals(that.organisation)) return false;
        if (!begin.equals(that.begin)) return false;
        if (!end.equals(that.end)) return false;
        if (!header.equals(that.header)) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = organisation.hashCode();
        result = 31 * result + begin.hashCode();
        result = 31 * result + end.hashCode();
        result = 31 * result + header.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
