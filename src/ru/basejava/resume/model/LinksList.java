package ru.basejava.resume.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class LinksList {
    private static LinksList instance;
    private final List<Link> links = new ArrayList<>();

    private LinksList() {
    }

    public static LinksList getInstance() {
        if (instance == null) {
            instance = new LinksList();
        }
        return instance;
    }

    public Link get(Link link) {
        if (!links.contains(link)) {
            links.add(link);
        }
        return links.get(links.indexOf(link));
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Link implements Serializable {
        private static final long serialVersionUID = 1L;

        private String name;
        private String url;

        public Link() {
        }

        public Link(String name, String url) {
            Objects.requireNonNull(name, "Name of the URL shouldn't be null");
            this.name = name;
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "Link{" +
                    "name='" + name + '\'' +
                    ", url='" + url + '\'' +
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

            Link link = (Link) o;

            if (!name.equals(link.name)) {
                return false;
            }
            return Objects.equals(url, link.url);
        }

        @Override
        public int hashCode() {
            int result = name.hashCode();
            result = 31 * result + (url != null ? url.hashCode() : 0);
            return result;
        }
    }
}
