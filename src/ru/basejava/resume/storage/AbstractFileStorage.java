package ru.basejava.resume.storage;

import ru.basejava.resume.exception.StorageException;
import ru.basejava.resume.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " isn't directory path");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " isn't accessible for R/W");
        }
        this.directory = directory;
    }

    @Override
    void insertAt(File file, Resume resume) {
        boolean opResult;
        try {
            opResult = file.createNewFile();
            doWrite(file, resume);
        } catch (IOException e) {
            throw new StorageException("Can't save resume to file.", file.getName(), e);
        }
        if(!opResult) throw new StorageException("Can't save resume to file. File already exists.", file.getName());
    }

    protected abstract void doWrite(File file, Resume resume) throws IOException;

    @Override
    void deleteAt(File file) {
        if (!file.delete()) {
            throw new StorageException("Can't delete file.", file.getName());
        }
    }

    @Override
    void updateAt(File file, Resume resume) {
        try {
            doWrite(file, resume);
        } catch (IOException e) {
            throw new StorageException("Can't save resume to file.", file.getName(), e);
        }
    }

    @Override
    Resume getAt(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("Can't read resume from file.", file.getName(), e);
        }
    }

    protected abstract Resume doRead(File file) throws IOException;

    @Override
    boolean checkKeyExist(File file) {
        return file.exists();
    }

    @Override
    File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    List<Resume> getStorageAsList() {
        final File[] files = directory.listFiles();
        List<Resume> fileList = new LinkedList<>();
        if (files == null) return fileList;
        for (File file : files) {
            if (!file.isFile()) continue;
            try {
                fileList.add(doRead(file));
            } catch (IOException e) {
                throw new StorageException("Can't read resume from file.", file.getName(), e);
            }
        }
        return fileList;
    }

    @Override
    public void clear() {
        final File[] files = directory.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (!file.isFile()) continue;
            if (!file.delete()) throw new StorageException("Can't clear directory.", directory.getName());
        }
    }

    @Override
    public int size() {
        final File[] files = directory.listFiles();
        int counter = 0;
        if (files == null) return counter;
        for (File file : files) {
            if (!file.isFile()) continue;
            counter++;
        }
        return counter;
    }
}
