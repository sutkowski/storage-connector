package pl.sutkowski.storageconnector.filesystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import pl.sutkowski.api.FileHolder;
import pl.sutkowski.api.FileLocationHolder;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.api.exception.FileStorageException;
import pl.sutkowski.api.impl.ByteFileHolder;
import pl.sutkowski.api.impl.PathFileLocationHolder;

public class FileSystemFileStorage
        implements FileStorage {

    private final Path baseDirectory;

    public FileSystemFileStorage(String baseDirectory) throws IOException {
        this.baseDirectory = Paths.get(baseDirectory);
        createDirectoryIfNotExists(this.baseDirectory);
    }

    @Override
    public FileHolder download(FileLocationHolder url) {
        try {
            return new ByteFileHolder(Files.readAllBytes(resolveAbsolutePath(url).getPath()));
        } catch (IOException ex) {
            throw new FileStorageException(ex);
        }
    }

    @Override
    public void remove(FileLocationHolder url) {
        if (!resolveAbsolutePath(url).getPath().toFile().delete()) {
            throw FileStorageException.fileNotFound(url.getPath());
        }
    }

    @Override
    public FileLocationHolder upload(FileHolder content, FileLocationHolder url) {
        try {
            FileLocationHolder path = resolveAbsolutePath(url);
            createDirectoryIfNotExists(path.getPath().getParent());
            Files.write(path.getPath(), content.getBytes());
            return url;
        } catch (IOException e) {
            throw FileStorageException.uploadFailed(e);
        }
    }

    private void createDirectoryIfNotExists(Path parent) {
        if (!Files.exists(parent)) {
            try {
                Files.createDirectories(parent);
            } catch (IOException e) {
                throw FileStorageException.pathNotFound();
            }
        }
    }

    FileLocationHolder resolveAbsolutePath(FileLocationHolder url) {
        if (url == null) {
            throw FileStorageException.pathNotFound();
        }
        if (url.getPath().startsWith("\\") || url.getPath().startsWith("/")) {
            return new PathFileLocationHolder(baseDirectory.resolve(url.toString().substring(1)));
        }
        return new PathFileLocationHolder(baseDirectory.resolve(url.getPath()));
    }

}
