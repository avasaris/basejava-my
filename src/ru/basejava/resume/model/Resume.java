package ru.basejava.resume.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

public class Resume {

    private final String uuid;
    private String fullName;
    public Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);
    public Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Resume resume = (Resume) o;
//
//        return uuid.equals(resume.uuid);
//    }

//    @Override
//    public int hashCode() {
//        return uuid.hashCode();
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

     //   if (!uuid.equals(resume.uuid)) return false;
     //   return fullName.equals(resume.fullName);
        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UUID = " + uuid + " fullName = " + fullName;
    }
}
