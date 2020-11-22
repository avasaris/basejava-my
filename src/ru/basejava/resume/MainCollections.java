package ru.basejava.resume;

import ru.basejava.resume.model.Resume;

import java.util.Arrays;
import java.util.List;

public class MainCollections {
    public static void main(String[] args) {

        Resume r1 = new Resume("uuid1", "");
        Resume r2 = new Resume("uuid2", "");
        Resume r3 = new Resume("uuid3", "");

        List<Resume> resumes = Arrays.asList(r1, r2, r3);
        //resumes.remove(1);
        System.out.println(resumes);

    }
}
