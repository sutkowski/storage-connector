package pl.sutkowski.storageconnector.filesystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.api.exception.FileStorageException;

public class FileSystemFileStorage
        implements FileStorage {

    private final Path baseDirectory;

    public FileSystemFileStorage(String baseDirectory) throws IOException {
        this.baseDirectory = Paths.get(baseDirectory);
        createDirectoryIfNotExists(this.baseDirectory);
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
    public Path upload(byte[] content, Path url) {
        try {
            Path path = resolveAbsolutePath(url);
            createDirectoryIfNotExists(path.getParent());
            Files.write(path, content);
            return url;
        } catch (IOException e) {
            throw FileStorageException.uploadFailed(e);
        }
    }

    private void createDirectoryIfNotExists(Path parent)  {
        if (!Files.exists(parent)) {
            try {
                Files.createDirectories(parent);
            } catch (IOException e) {
                throw FileStorageException.pathNotFound();
            }
        }
    }

    Path resolveAbsolutePath(Path url) {
        if(url == null){
            throw FileStorageException.pathNotFound();
        }
        if (url.startsWith("\\") || url.startsWith("/")) {
            return baseDirectory.resolve(url.toString().substring(1));
        }
        return baseDirectory.resolve(url);
    }

}
