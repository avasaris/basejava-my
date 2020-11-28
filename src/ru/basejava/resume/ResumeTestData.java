package ru.basejava.resume;

import ru.basejava.resume.model.LuphdSection;
import ru.basejava.resume.model.Resume;
import ru.basejava.resume.model.SectionType;

import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Test Data");

        String personal = "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям";
        resume.sections.put(SectionType.PERSONAL, personal);

        String objective = "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.";
        resume.sections.put(SectionType.OBJECTIVE, objective);

        resume.sections.put(SectionType.ACHIEVEMENT, new ArrayList<>());
        ((List<String>) resume.sections.get(SectionType.ACHIEVEMENT)).add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        ((List<String>) resume.sections.get(SectionType.ACHIEVEMENT)).add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");

        resume.sections.put(SectionType.QUALIFICATIONS, new ArrayList<>());
        ((List<String>) resume.sections.get(SectionType.QUALIFICATIONS)).add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        ((List<String>) resume.sections.get(SectionType.QUALIFICATIONS)).add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");

        LuphdSection experience = new LuphdSection();
        experience.header = "Автор проекта.";
        experience.period = "10/2013 - Сейчас";
        experience.linkName = "Java Online Projects";
        experience.URL = "http://javaops.ru/";
        experience.description = "Создание, организация и проведение Java онлайн проектов и стажировок.";
        List<LuphdSection> experiences = new ArrayList<>();
        experiences.add(experience);
        resume.sections.put(SectionType.EXPERIENCE, experiences);

        LuphdSection education = new LuphdSection();
        education.header = "\"Functional Programming Principles in Scala\" by Martin Odersky";
        education.period = "03/2013 - 05/2013";
        education.linkName = "Coursera";
        education.URL = "https://www.coursera.org/course/progfun";
        List<LuphdSection> educations = new ArrayList<>();
        educations.add(education);
        resume.sections.put(SectionType.EDUCATION, educations);

    }
}
