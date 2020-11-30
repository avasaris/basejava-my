package ru.basejava.resume.model;

import java.time.YearMonth;

public class ExtendedSection implements AbstractSection {
    private final String urlName;
    private final String url;
    private final YearMonth begin;
    private final YearMonth end;
    private final String header;
    private final String description;

    public ExtendedSection(String urlName, String url, YearMonth begin, YearMonth end, String header, String description) {
        this.urlName = urlName;
        this.url = url;
        this.begin = begin;
        this.end = end;
        this.header = header;
        this.description = description;
    }

    @Override
    public String toString() {
        return "ExtendedSection{" +
                "urlName='" + urlName + '\'' +
                ", url='" + url + '\'' +
                ", begin=" + begin +
                ", end=" + end +
                ", header='" + header + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}