package ru.basejava.resume.model;

import java.time.YearMonth;
import java.util.Objects;

public class Experience {
    private final YearMonth begin;
    private final YearMonth end;
    private final String header;
    private final String description;

    public Experience(YearMonth begin, YearMonth end, String header, String description) {
        Objects.requireNonNull(begin, "Experience.begin sould't be null");
        Objects.requireNonNull(end, "Experience.end sould't be null");
        Objects.requireNonNull(header, "Experience.header sould't be null");
        this.begin = begin;
        this.end = end;
        this.header = header;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Experience{" +
                "begin=" + begin +
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

        if (!begin.equals(that.begin)) return false;
        if (!end.equals(that.end)) return false;
        if (!header.equals(that.header)) return false;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        int result = begin.hashCode();
        result = 31 * result + end.hashCode();
        result = 31 * result + header.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }
}
