package pl.sutkowski.storageconnector.memory;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.api.exception.FileStorageException;

public class InMemoryFileStorage
        implements FileStorage {

    private static final Map<Path, byte[]> FILES = Collections.synchronizedMap(new HashMap<>());
    private static final AtomicLong ID = new AtomicLong();

    @Override
    public byte[] download(Path url) {
        return Optional.ofNullable(FILES.get(url)).orElseThrow(() ->
                FileStorageException.fileNotFound(url)
        );
    }

    @Override
    public void remove(Path url) {
        Optional.ofNullable(FILES.remove(url)).orElseThrow(() ->
                FileStorageException.fileNotFound(url)
        );
    }

    @Override
    public Path upload(byte[] content, Path url) {
        FILES.put(url, content);
        return url;
    }

}
