package pl.sutkowski.api;

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
}
