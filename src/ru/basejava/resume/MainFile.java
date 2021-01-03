package ru.basejava.resume;

import java.io.File;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) throws IOException {
        printDir("./");
    }

    private static void printDir(String path) throws IOException {
        File dir = new File(path);
        if (dir.isDirectory()) {
            String[] list = dir.list();
            if (list == null) return;
            for (String name : list) {
                if (!name.equals(".git") && !name.equals(".idea")) {
                    printDir(path + "/" + name);
                }
            }
        }
        System.out.println(dir.getCanonicalPath());
    }
}
