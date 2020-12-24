package ru.basejava.resume.storage;

import ru.basejava.resume.exception.StorageException;
import ru.basejava.resume.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

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

        try {
            file.createNewFile();
            doWrite(file, resume);
        } catch (IOException e) {
            throw new StorageException("Can't save resume to file.", file.getName(), e);
        }
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
        // abstract doRead()
        return null;
    }

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
        // return all files (use doRead()) from directory
        return null;
    }

    @Override
    public void clear() {
        // delete all files into directory
    }

    @Override
    public int size() {
        // count files in directory
        return 0;
    }
}
