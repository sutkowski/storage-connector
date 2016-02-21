package pl.sutkowski.storageconnector.memory;

import pl.sutkowski.api.FileStorage;
import pl.sutkowski.api.exception.FileStorageException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

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
    public Path upload(byte[] content) {
        final long newId = ID.incrementAndGet();
        final Path url = Paths.get(Long.toString(newId));
        FILES.put(url, content);
        return url;
    }
}
