package ru.basejava.resume.model;

public enum ContactType {
    PHONE("Тел: ", ""),
    SKYPE("Skype: ", ""),
    EMAIL("Почта: ", ""),
    LINKEDIN("", "Профиль LinkedIn"),
    GITHUB("", "Профиль GitHub"),
    STACKOVERFLOW("", "Профиль Stackoverflow"),
    HOMEPAGE("", "Домашняя страница");

    private final String name;
    private final String description;

    ContactType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
