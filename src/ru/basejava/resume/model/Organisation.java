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

public class Organisation implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<Position> positions = new ArrayList<>();
    private LinksList.Link link;

    public Organisation() {
    }

    public Organisation(LinksList.Link link, Position... positions) {
        Objects.requireNonNull(link, "Link to organisation shouldn't be null.");
        Objects.requireNonNull(positions, "Position or education shouldn't be null.");
        setLink(link);
        Collections.addAll(this.positions, positions);
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions.clear();
        Collections.addAll(this.positions, positions.toArray(new Position[0]));
    }

    public LinksList.Link getLink() {
        return link;
    }

    public void setLink(LinksList.Link link) {
        LinksList ll = LinksList.getInstance();
        this.link = ll.get(link);
    }

    public void addPosition(Position position) {
        positions.add(position);
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Organisation that = (Organisation) o;

        if (!link.equals(that.link)) {
            return false;
        }
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

        public YearMonth getBegin() {
            return begin;
        }

        public void setBegin(YearMonth begin) {
            this.begin = begin;
        }

        public YearMonth getEnd() {
            return end;
        }

        public void setEnd(YearMonth end) {
            this.end = end;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
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

            Position position = (Position) o;

            if (!begin.equals(position.begin)) return false;
            if (!end.equals(position.end)) return false;
            if (!header.equals(position.header)) return false;
            return description != null ? description.equals(position.description) : position.description == null;
        }

        @Override
        public int hashCode() {
            int result = begin.hashCode();
            result = 31 * result + end.hashCode();
            result = 31 * result + header.hashCode();
            result = 31 * result + (description != null ? description.hashCode() : 0);
            return result;
        }
    }
}