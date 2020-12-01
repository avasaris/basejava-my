package ru.basejava.resume;

import ru.basejava.resume.model.*;

public class ResumeTestData {
    public static void main(String[] args) {

        Resume resume = new Resume("Resume Test Data");

        resume.contacts.put(ContactType.PHONE, "+7(921) 855-0482");
        resume.contacts.put(ContactType.SKYPE, "grigory.kislin");
        resume.contacts.put(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.contacts.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.contacts.put(ContactType.GITHUB, "https://github.com/gkislin");
        resume.contacts.put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume.contacts.put(ContactType.HOMEPAGE, "http://gkislin.ru/");

        for (ContactType contactType : ContactType.values()) {
            String contact = "";
            String title = contactType.getTitle();
            String value = resume.contacts.get(contactType);
            switch (contactType) {
                case PHONE:
                    contact = title + value;
                    break;
                case SKYPE:
                    contact = title + "<a href='skype:" + value + "'>" + value + "<a>";
                    break;
                case EMAIL:
                    contact = title + "<a href='mailto:" + value + "'>" + value + "<a>";
                    break;
                default:
                    contact = "<a href='" + value + "'>" + title + "<a>";
            }
            System.out.println(contact);
        }


        resume.sections.put(SectionType.PERSONAL, new SingleLineSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.sections.put(SectionType.OBJECTIVE, new SingleLineSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        resume.sections.put(SectionType.ACHIEVEMENT, new BulletedListSection(
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk."));

        resume.sections.put(SectionType.QUALIFICATIONS, new BulletedListSection(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce"));

//        resume.sections.put(SectionType.EXPERIENCE, new Experience(
//                "Java Online Projects",
//                "http://javaops.ru/",
//                YearMonth.now(),
//                YearMonth.now(),
//                "Автор проекта.",
//                "Создание, организация и проведение Java онлайн проектов и стажировок."));
//
//        resume.sections.put(SectionType.EDUCATION, new Experience(
//                "Coursera",
//                "https://www.coursera.org/course/progfun",
//                YearMonth.now(),
//                YearMonth.now(),
//                "\"Functional Programming Principles in Scala\" by Martin Odersky",
//                ""));

        for (SectionType section : SectionType.values()) {
            System.out.println(section.getTitle() + ":\n\t" + resume.sections.get(section));
        }
    }


}
