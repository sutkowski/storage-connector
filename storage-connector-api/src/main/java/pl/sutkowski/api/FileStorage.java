package pl.sutkowski.api;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import pl.sutkowski.api.exception.FileStorageException;
import pl.sutkowski.api.impl.FileLocationFactory;

public interface FileStorage {

    default FileLocationHolder upload(FileHolder content) {
        final String newId = UUID.randomUUID().toString();
        final Path url = Paths.get("/" + newId);

        return upload(content, FileLocationFactory.of(url));
    }

    FileHolder download(FileLocationHolder url);

    void remove(FileLocationHolder url);

    FileLocationHolder upload(FileHolder content, FileLocationHolder url);

    default FileLocationHolder move(FileLocationHolder fromPath, FileLocationHolder toPath) {
        try {
            upload(download(fromPath), toPath);
            remove(fromPath);
            return toPath;
        } catch (FileStorageException e) {
            throw FileStorageException.moveException(fromPath.toString(), toPath.toString());
        }
    }
}
