package ru.basejava.resume;

import ru.basejava.resume.model.*;

import java.time.YearMonth;
import java.util.Random;
import java.util.UUID;

public class ResumeTestData {
    private static final String[] phones = {"+7(921) 855-0482", "+7(223) 445-2020", "+7(223) 000-0010", "+7(552) 938-8726"};
    private static final String[] skypes = {"anton.belov", "grigory.kislin", "sergey.ivanov", "petr.tolstoy"};
    private static final String[] emails = {"anton@belov.ru", "grigory@kislin.org", "sergey@ivanov.com", "petr@tolstoy.net"};
    private static final String[] nicNames = {"abelov1995", "gkislin", "s_ivanov", "petr-tolst"};
    private static final String[] homePages = {"http://anton.belov.ru/", "http://grigory.kislin.ru", "https://sergey.ivanov", "https://petr.tolstoy.net"};
    private static final String[] positions = {
            "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям",
            "Архитектор встроенных систем",
            "Аналитик",
            "Ведущий инженер-разработчик"
    };

    private static final String[] objectives = {
            "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.",
            "Инициативность, коммуникабельность.",
            "Покладистый и исполнительный.",
            "Харизматичный лидер, играю в футбол."
    };

    private static final String[] achievements = {
            "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
            "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
            "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
            "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
            "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
            "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."
    };

    private static final String[] qualifications = {
            "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
            "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
            "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB",
            "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy, XML/XSD/XSLT, SQL, C/C++, Unix shell scripts",
            "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).",
            "Python: Django.",
            "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js",
            "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka",
            "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.",
            "Инструменты: Maven + plugin development, Gradle, настройка Ngnix, администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.",
            "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектирования, архитектурных шаблонов, UML, функционального программирования",
            "Родной русский, английский \"upper intermediate\""
    };

    private static final Random random = new Random();

    public static void fillResume(Resume resume, String... configs) {
        randomContacts(resume);
        for (String config : configs) {
            switch (config) {
                case "personal":
                    resume.addSection(SectionType.PERSONAL, new TextSection(getRandom(positions)));
                    break;
                case "objective":
                    resume.addSection(SectionType.OBJECTIVE, new TextSection(getRandom(objectives)));
                    break;
                case "section1":
                    sections1(resume);
                    break;
                case "section2":
                    sections2(resume);
                    break;
            }
        }
    }

    private static void randomContacts(Resume resume) {
        resume.addContact(ContactType.PHONE, getRandom(phones));
        resume.addContact(ContactType.SKYPE, getRandom(skypes));
        resume.addContact(ContactType.EMAIL, getRandom(emails));
        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/" + getRandom(nicNames));
        resume.addContact(ContactType.GITHUB, "https://github.com/" + getRandom(nicNames));
        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/" + getRandom(nicNames));
        resume.addContact(ContactType.HOMEPAGE, getRandom(homePages));
    }

    private static void sections1(Resume resume) {
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(achievements[0], achievements[1]));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(qualifications[0], qualifications[1], qualifications[2]));

        OrganisationSection experienceSection = new OrganisationSection();
        experienceSection.add(
                new Organisation(
                        new LinksList.Link("Java Online Projects", null),
                        new Organisation.Position(
                                YearMonth.parse("2013-10"),
                                YearMonth.parse("2020-12"),
                                "Автор проекта.",
                                null),
                        new Organisation.Position(
                                YearMonth.parse("2011-04"),
                                YearMonth.parse("2012-10"),
                                "Java архитектор",
                                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интеграционных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python")
                ));
        experienceSection.add(
                new Organisation(
                        new LinksList.Link("Wrike", "https://www.wrike.com/"),
                        new Organisation.Position(
                                YearMonth.parse("2014-10"),
                                YearMonth.parse("2016-01"),
                                "Старший разработчик (backend)",
                                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")));
        resume.addSection(SectionType.EXPERIENCE, experienceSection);

        OrganisationSection educationSection = new OrganisationSection();
        educationSection.add(
                new Organisation(
                        new LinksList.Link("Coursera", "https://www.coursera.org/course/progfun"),
                        new Organisation.Position(
                                YearMonth.parse("2013-10"),
                                YearMonth.parse("2020-12"),
                                "\"Functional Programming Principles in Scala\" by Martin Odersky",
                                "")));
        resume.addSection(SectionType.EDUCATION, educationSection);
    }

    private static void sections2(Resume resume) {
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(achievements[2], achievements[3], achievements[4]));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(qualifications[3], qualifications[4]));

        OrganisationSection experienceSection = new OrganisationSection();
        experienceSection.add(
                new Organisation(
                        new LinksList.Link("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/"),
                        new Organisation.Position(
                                YearMonth.parse("2010-12"),
                                YearMonth.parse("2012-04"),
                                "Ведущий программист",
                                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.")));
        experienceSection.add(
                new Organisation(
                        new LinksList.Link("Alcatel", "http://www.alcatel.ru/"),
                        new Organisation.Position(
                                YearMonth.parse("1997-09"),
                                YearMonth.parse("2005-01"),
                                "Инженер по аппаратному и программному тестированию",
                                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).")));
        resume.addSection(SectionType.EXPERIENCE, experienceSection);

        OrganisationSection educationSection = new OrganisationSection();
        educationSection.add(
                new Organisation(
                        new LinksList.Link("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366"),
                        new Organisation.Position(
                                YearMonth.parse("2011-03"),
                                YearMonth.parse("2011-04"),
                                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"",
                                "")));
        educationSection.add(
                new Organisation(
                        new LinksList.Link("Siemens AG", "http://www.siemens.ru/"),
                        new Organisation.Position(
                                YearMonth.parse("2005-01"),
                                YearMonth.parse("2005-04"),
                                "3 месяца обучения мобильным IN сетям (Берлин)",
                                "")));
        resume.addSection(SectionType.EDUCATION, educationSection);
    }

    public static void printResume(Resume resume) {
        System.out.println("\n=============== " + resume.getFullName() + " ===============");
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

        for (SectionType sectionType : SectionType.values()) {
            System.out.println("\n" + sectionType.getTitle());
            System.out.println(resume.getSections().get(sectionType));
        }
    }

    public static void main(String[] args) {
        Resume resume = new Resume(UUID.randomUUID().toString(), "Resume Test Data");
        fillResume(resume, "personal", "section2");
        printResume(resume);
    }

    private static String getRandom(String[] array) {
        return array[random.nextInt(array.length)];
    }
}
