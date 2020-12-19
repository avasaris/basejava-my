package ru.basejava.resume;

import java.io.File;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) throws IOException {
        File file = new File("./.gitignore");
        System.out.println(file.getCanonicalPath());
    }
}
