package pl.sutkowski.api;

import pl.sutkowski.api.exception.FileStorageException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public interface FileStorage {

    default Path upload(byte[] content) {
        final String newId = UUID.randomUUID().toString();
        final Path url = Paths.get("/" + newId);

        return upload(content, url);
    }

    byte[] download(Path url);

    void remove(Path url);

    Path upload(byte[] content, Path url);

    default Path move(Path fromPath, Path toPath) {
        try {
            upload(download(fromPath), toPath);
            remove(fromPath);
            return toPath;
        } catch (FileStorageException e) {
            throw FileStorageException.moveException(fromPath.toString(), toPath.toString());
        }
    }
}
