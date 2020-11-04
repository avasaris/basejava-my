package ru.basejava.resume;

import ru.basejava.resume.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class MainReflection {
    public static void main(String[] args) {
        Resume resume = new Resume("my-very-uniq-uuid", "");

        System.out.println("------ getDeclaredFields ---------------------");

        Field[] fields = resume.getClass().getDeclaredFields();
        Arrays.stream(fields).forEach(field -> System.out.println(field.getName()));

        System.out.println("------ getDeclaredMethods --------------------");

        Method[] declaredMethods = resume.getClass().getDeclaredMethods();
        Arrays.stream(declaredMethods).forEach(method -> System.out.println(method.getName()));

        System.out.println("------ invoke --------------------------------");

        Arrays.stream(declaredMethods)
                .filter(m -> m.getName().equals("toString"))
                .forEach(m -> {
                    try {
                        System.out.println(m.invoke(resume));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });


    }
}
