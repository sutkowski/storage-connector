package pl.sutkowski.storageconnector.filesystem;

import pl.sutkowski.api.FileStorage;
import pl.sutkowski.api.exception.FileStorageException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileSystemFileStorage
        implements FileStorage {

    private final Path baseDirectory;

    public FileSystemFileStorage(String baseDirectory) throws IOException {
        this.baseDirectory = Paths.get(baseDirectory);
        if (!Files.exists(this.baseDirectory)) {
            Files.createDirectories(this.baseDirectory);
        }
    }

    @Override
    public byte[] download(Path url) {
        try {
            return Files.readAllBytes(resolveAbsolutePath(url));
        } catch (IOException ex) {
            throw new FileStorageException(ex);
        }
    }

    @Override
    public void remove(Path url) {
        if (!resolveAbsolutePath(url).toFile().delete()) {
            throw FileStorageException.fileNotFound(url);
        }
    }

    @Override
    public Path upload(byte[] content) {
        final String newId = UUID.randomUUID().toString();
        try {
            final Path url = Paths.get(newId);
            Files.write(resolveAbsolutePath(url), content);
            return url;
        } catch (IOException e) {
            throw FileStorageException.uploadFailed(e);
        }
    }

    protected Path resolveAbsolutePath(Path url) {
        return baseDirectory.resolve(url);
    }

}
