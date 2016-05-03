package pl.sutkowski.api;

import pl.sutkowski.api.exception.FileStorageException;
import pl.sutkowski.api.impl.PathFileLocationHolder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public interface FileStorage {

    default FileLocationHolder upload(FileHolder content) {
        final String newId = UUID.randomUUID().toString();
        final Path url = Paths.get("/" + newId);

        return upload(content, produceFileLocationHolder(url));
    }

    FileHolder download(FileLocationHolder url);

    void remove(FileLocationHolder url);

    FileLocationHolder upload(FileHolder content, FileLocationHolder url);

    default FileLocationHolder move(FileLocationHolder fromPath, FileLocationHolder toPath) {
        try {
            final FileHolder download = download(fromPath);
            final FileLocationHolder upload = upload(download, toPath);
            remove(fromPath);
            return upload;
        } catch (FileStorageException e) {
            throw FileStorageException.moveException(fromPath.toString(), toPath.toString());
        }
    }

    default FileLocationHolder produceFileLocationHolder(Path path) {
        return new PathFileLocationHolder(path);
    }
}
