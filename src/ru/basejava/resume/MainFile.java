package ru.basejava.resume;

import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MainFile {
    private static final Set<String> EXCLUDE_DIRS = new HashSet<>();

    static {
        EXCLUDE_DIRS.add(".git");
        EXCLUDE_DIRS.add(".idea");
    }

    public static void main(String[] args) {
        walkInsideDir(".", 0);
    }

    private static void walkInsideDir(String dir, int level) {
        Objects.requireNonNull(dir);
        File name = new File(dir);
        printName(level, name);
        if (name.isDirectory()) {
            String[] dirList = name.list();
            if (dirList == null) return;
            for (String item : dirList) {
                if (!EXCLUDE_DIRS.contains(item)) {
                    walkInsideDir(dir + "/" + item, level + 1);
                }
            }
        }
    }

    private static void printName(int level, File dir) {
        String name = getName(dir);
        StringBuffer levelName = getLevelName(level, name);
        System.out.println(levelName);
    }

    private static StringBuffer getLevelName(int level, String name) {
        StringBuffer sb = new StringBuffer(name);
        for (int i = 0; i < level - 1; i++) sb.insert(0, '\t');
        return sb;
    }

    private static String getName(File dir) {
        String name = dir.getName();
        if (dir.isDirectory()) name += "/";
        return name;
    }
}
