package ru.basejava.resume.storage;

import ru.basejava.resume.exception.StorageException;
import ru.basejava.resume.model.Resume;
import ru.basejava.resume.storage.serializer.StreamSerializer;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;

    private final StreamSerializer streamSerializer;

    protected FileStorage(String dir, StreamSerializer streamSerializer) {
        Objects.requireNonNull(dir);
        directory = new File(dir);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " isn't directory path");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " isn't accessible for R/W");
        }
        this.streamSerializer = streamSerializer;
    }

    @Override
    void insertAt(File file, Resume resume) {
        boolean opResult;
        try {
            opResult = file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Can't create file for resume.", file.getName(), e);
        }
        if (!opResult) {
            throw new StorageException("Can't create file for resume. File already exists.", file.getName());
        }
        updateAt(file, resume);
    }

    @Override
    void deleteAt(File file) {
        if (!file.delete()) {
            throw new StorageException("Can't delete file.", file.getName());
        }
    }

    @Override
    void updateAt(File file, Resume resume) {
        try {
            streamSerializer.doWrite(new BufferedOutputStream(new FileOutputStream(file)), resume);
        } catch (IOException e) {
            throw new StorageException("Can't save resume to file.", file.getName(), e);
        }
    }

    @Override
    Resume getAt(File file) {
        try {
            return streamSerializer.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Can't read resume from file.", file.getName(), e);
        }
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
        List<Resume> fileList = new LinkedList<>();
        File[] files = directory.listFiles();
        if (files == null) {
            return fileList;
        }
        for (File file : files) {
            fileList.add(getAt(file));
        }
        return fileList;
    }

    @Override
    public void clear() {
        for (Resume resume : getStorageAsList()) {
            deleteAt(getSearchKey(resume.getUuid()));
        }
    }

    @Override
    public int size() {
        return getStorageAsList().size();
    }


}
