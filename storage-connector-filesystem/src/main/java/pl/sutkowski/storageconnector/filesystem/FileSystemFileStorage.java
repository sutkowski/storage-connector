package pl.sutkowski.storageconnector.filesystem;

import pl.sutkowski.api.FileHolder;
import pl.sutkowski.api.FileLocationHolder;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.api.exception.FileStorageException;
import pl.sutkowski.api.impl.ByteFileHolder;
import pl.sutkowski.api.impl.PathFileLocationHolder;
import pl.sutkowski.api.utils.FileStorageUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystemFileStorage
        implements FileStorage {

    private final Path baseDirectory;

    public FileSystemFileStorage(String baseDirectory) throws IOException {
        this.baseDirectory = Paths.get(baseDirectory);
        FileStorageUtils.createDirectoryIfNotExists(this.baseDirectory);
    }

    @Override
    public FileHolder download(FileLocationHolder url) {
        try {
            return new ByteFileHolder(Files.readAllBytes(resolveAbsolutePath(url).getPath()));
        } catch (IOException ex) {
            throw FileStorageException.downloadFailed(ex);
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
            FileStorageUtils.createDirectoryIfNotExists(path.getPath().getParent());
            Files.write(path.getPath(), content.getBytes());
            return url;
        } catch (IOException e) {
            throw FileStorageException.uploadFailed(e);
        }
    }

    FileLocationHolder resolveAbsolutePath(FileLocationHolder url) {
        if (url == null) {
            throw FileStorageException.pathNotFound();
        }
        if (url.getPath().startsWith("\\") || url.getPath().startsWith("/")) {
            return new PathFileLocationHolder(baseDirectory.resolve(url.getPath().toString().substring(1)));
        }
        return new PathFileLocationHolder(baseDirectory.resolve(url.getPath()));
    }

}
