package ru.basejava.resume;

import ru.basejava.resume.model.Resume;
import ru.basejava.resume.storage.ArrayStorage;
import ru.basejava.resume.storage.Storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Interactive test for ru.basejava.resume.storage.ArrayStorage implementation
 * (just run, no need to understand)
 */
public class MainArray {
    private static final Storage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume r;
        while (true) {
            System.out.print("Введите одну из команд - (list | save uuid {name} | delete uuid | get uuid | update uuid {name} | clear | exit): ");
            String[] params = reader.readLine().trim().split(" ");
            String name = "";
            if (params.length == 3) {
                name = params[2];
            } else if (params.length < 1 || params.length > 2) {
                System.out.println("Неверная команда.");
                continue;
            }
            String uuid = null;
            if (params.length > 1) {
                uuid = params[1].toLowerCase().intern();
            }
            switch (params[0].toLowerCase()) {
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(ARRAY_STORAGE.size());
                    break;
                case "save":
                    r = new Resume(uuid, name);
                    ARRAY_STORAGE.save(r);
                    printAll();
                    break;
                case "delete":
                    ARRAY_STORAGE.delete(uuid);
                    printAll();
                    break;
                case "get":
                    System.out.println(ARRAY_STORAGE.get(uuid));
                    break;
                case "clear":
                    ARRAY_STORAGE.clear();
                    printAll();
                    break;
                case "update":
                    r = new Resume(uuid, name);
                    ARRAY_STORAGE.update(r);
                    printAll();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    static void printAll() {
        List<Resume> all = ARRAY_STORAGE.getAllSorted();
        System.out.println("----------------------------");
        if (all.isEmpty()) {
            System.out.println("Empty");
        } else {
            for (Resume r : all) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }
}