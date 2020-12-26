package ru.basejava.resume.model;

import java.time.YearMonth;
import java.util.*;

public class Organisation implements Section {
    private final Link link;
    private final List<Position> positions = new ArrayList<>();

    public Organisation(Link link, Position... positions) {
        Objects.requireNonNull(link, "Link to organisation shouldn't be null.");
        Objects.requireNonNull(positions, "Position or education shouldn't be null.");
        this.link = LinksList.get(link);
        Collections.addAll(this.positions, positions);
    }

    public List<Position> getExperiences() {
        return positions;
    }

    @Override
    public String toString() {
        return "Organisation{" +
                "link=" + link +
                '}';
    }

    public static class Position {
        private final YearMonth begin;
        private final YearMonth end;
        private final String header;
        private final String description;

        public Position(YearMonth begin, YearMonth end, String header, String description) {
            Objects.requireNonNull(begin, "Position.begin sould't be null");
            Objects.requireNonNull(end, "Position.end sould't be null");
            Objects.requireNonNull(header, "Position.header sould't be null");
            this.begin = begin;
            this.end = end;
            this.header = header;
            this.description = description;
        }

        @Override
        public String toString() {
            return "Position{" +
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

            Position that = (Position) o;

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
}
