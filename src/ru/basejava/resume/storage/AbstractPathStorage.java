package ru.basejava.resume.storage;

import ru.basejava.resume.exception.StorageException;
import ru.basejava.resume.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory);
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " isn't directory or isn't accessible for R/W");
        }
    }

    protected abstract void doWrite(OutputStream os, Resume resume) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

    @Override
    void insertAt(Path file, Resume resume) {
        updateAt(file, resume);
    }

    @Override
    void deleteAt(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new StorageException("Can't delete path.", file.toString(), e);
        }
    }

    @Override
    void updateAt(Path path, Resume resume) {
        try (OutputStream file = Files.newOutputStream(path)) {
            doWrite(new BufferedOutputStream(file), resume);
        } catch (IOException e) {
            throw new StorageException("Can't save resume to path.", path.toString(), e);
        }
    }

    @Override
    Resume getAt(Path path) {
        try (InputStream file = Files.newInputStream(path)) {
            return doRead(new BufferedInputStream(file));
        } catch (IOException e) {
            throw new StorageException("Can't read resume from path.", path.toString(), e);
        }
    }

    @Override
    boolean checkKeyExist(Path file) {
        return Files.exists(file);
    }

    @Override
    Path getSearchKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    List<Resume> getStorageAsList() {
        try {
            return Files.list(directory).map(this::getAt).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Path storage list error", "", e);
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteAt);
        } catch (IOException e) {
            throw new StorageException("Path storage clear error", "", e);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).filter(Files::isRegularFile).count();
        } catch (IOException e) {
            throw new StorageException("Path storage size error", "", e);
        }
    }
}
