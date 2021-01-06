package ru.basejava.resume.model;

import ru.basejava.resume.util.YearMonthAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organisation implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<Position> positions = new ArrayList<>();
    private LinksList.Link link;

    public Organisation() {
    }

    public Organisation(LinksList.Link link, Position... positions) {
        Objects.requireNonNull(link, "Link to organisation shouldn't be null.");
        Objects.requireNonNull(positions, "Position or education shouldn't be null.");
        LinksList ll = LinksList.getInstance();
        this.link = ll.get(link);
        Collections.addAll(this.positions, positions);
    }

    public List<Position> getPositions() {
        return positions;
    }

    public LinksList.Link getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "\nOrganisation{" +
                "link=" + link +
                ",\n\t positions=" + positions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organisation that = (Organisation) o;

        if (!link.equals(that.link)) return false;
        return positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        int result = link.hashCode();
        result = 31 * result + positions.hashCode();
        return result;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {
        private static final long serialVersionUID = 1L;

        @XmlJavaTypeAdapter(YearMonthAdapter.class)
        private YearMonth begin;
        @XmlJavaTypeAdapter(YearMonthAdapter.class)
        private YearMonth end;
        private String header;
        private String description;

        public Position() {
        }

        public Position(YearMonth begin, YearMonth end, String header, String description) {
            Objects.requireNonNull(begin, "Position.begin shouldn't be null");
            Objects.requireNonNull(end, "Position.end shouldn't be null");
            Objects.requireNonNull(header, "Position.header shouldn't be null");
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
                    "}\n";
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
