package ru.basejava.resume.storage;

import ru.basejava.resume.exception.StorageException;
import ru.basejava.resume.model.Resume;
import ru.basejava.resume.storage.serializer.SerializerStrategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;

    private final SerializerStrategy streamStorage;

    protected PathStorage(String dir, SerializerStrategy streamStorage) {
        Objects.requireNonNull(dir);
        directory = Paths.get(dir);
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " isn't directory or isn't accessible for R/W");
        }
        this.streamStorage = streamStorage;
    }

    @Override
    void insertAt(Path file, Resume resume) {
        updateAt(file, resume);
    }

    @Override
    void deleteAt(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new StorageException("Can't delete path.", getFileName(file), e);
        }
    }

    @Override
    void updateAt(Path file, Resume resume) {
        try (OutputStream fileStream = Files.newOutputStream(file)) {
            streamStorage.doWrite(new BufferedOutputStream(fileStream), resume);
        } catch (IOException e) {
            throw new StorageException("Can't save resume to path.", getFileName(file), e);
        }
    }

    @Override
    Resume getAt(Path file) {
        try (InputStream fileStream = Files.newInputStream(file)) {
            return streamStorage.doRead(new BufferedInputStream(fileStream));
        } catch (IOException e) {
            throw new StorageException("Can't read resume from path.", getFileName(file), e);
        }
    }

    @Override
    boolean checkKeyExist(Path file) {
        return Files.isRegularFile(file);
    }

    @Override
    Path getSearchKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    List<Resume> getStorageAsList() {
        return getFilesList().map(this::getAt).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getFilesList().forEach(this::deleteAt);
    }

    @Override
    public int size() {
        return (int) getFilesList().count();
    }

    private Stream<Path> getFilesList() {
        try {
            return Files.list(directory).filter(Files::isRegularFile);
        } catch (IOException e) {
            throw new StorageException("Path storage listing error", "", e);
        }
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }
}
