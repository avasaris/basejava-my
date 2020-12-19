package ru.basejava.resume;

import ru.basejava.resume.model.*;

import java.time.YearMonth;
import java.util.EnumMap;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {

        Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

        contacts.put(ContactType.PHONE, "+7(921) 855-0482");
        contacts.put(ContactType.SKYPE, "grigory.kislin");
        contacts.put(ContactType.EMAIL, "gkislin@yandex.ru");
        contacts.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        contacts.put(ContactType.GITHUB, "https://github.com/gkislin");
        contacts.put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        contacts.put(ContactType.HOMEPAGE, "http://gkislin.ru/");


        Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

        sections.put(SectionType.PERSONAL, new SingleLineSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        sections.put(SectionType.OBJECTIVE, new SingleLineSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        sections.put(SectionType.ACHIEVEMENT, new BulletedListSection(
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk."));

        sections.put(SectionType.QUALIFICATIONS, new BulletedListSection(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce"));

        Organisations organisations = Organisations.getInstance();

        System.out.println(organisations);

        Experience experience = new Experience(
                organisations.get("Java Online Projects", "http://javaops.ru/"),
                YearMonth.now(),
                YearMonth.now(),
                "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок.");

        sections.put(SectionType.EXPERIENCE, new ExperienceSection(experience));

        System.out.println(organisations);

        Experience education = new Experience(
                //organisations.get("Coursera", "https://www.coursera.org/course/progfun"),
                organisations.get("Java Online Projects", "http://javaops.ru/"),
                YearMonth.now(),
                YearMonth.now(),
                "\"Functional Programming Principles in Scala\" by Martin Odersky",
                "");

        sections.put(SectionType.EDUCATION, new ExperienceSection(education));

        System.out.println(organisations);

        Resume resume = new Resume("Resume Test Data", contacts, sections);

        for (ContactType contactType : ContactType.values()) {
            String contact;
            String title = contactType.getTitle();
            String value = resume.getContacts().get(contactType);
            switch (contactType) {
                case PHONE:
                    contact = title + ": " + value;
                    break;
                case SKYPE:
                    contact = title + ": <a href='skype:" + value + "'>" + value + "<a>";
                    break;
                case EMAIL:
                    contact = title + ": <a href='mailto:" + value + "'>" + value + "<a>";
                    break;
                default:
                    contact = "<a href='" + value + "'>" + title + "<a>";
            }
            System.out.println(contact);
        }

        for (SectionType section : SectionType.values()) {
            System.out.println(section.getTitle() + ":\n\t" + resume.getSections().get(section));
        }
    }


}
