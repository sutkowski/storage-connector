package pl.sutkowski.storageconnector.memory;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import pl.sutkowski.api.FileHolder;
import pl.sutkowski.api.FileLocationHolder;
import pl.sutkowski.api.FileStorageImplementor;
import pl.sutkowski.api.exception.FileStorageException;
import pl.sutkowski.api.impl.PathFileLocationHolder;

public class InMemoryFileStorageImplementor
        implements FileStorageImplementor {

    private static final Map<Path, FileHolder > FILES = Collections.synchronizedMap(new ConcurrentHashMap<>());

    @Override
    public FileHolder download(FileLocationHolder url) {
        return Optional.ofNullable(FILES.get(url.getPath())).orElseThrow(() ->
                FileStorageException.fileNotFound(url.getPath())
        );
    }

    @Override
    public void remove(FileLocationHolder url) {
        Optional.ofNullable(FILES.remove(url.getPath())).orElseThrow(() ->
                FileStorageException.fileNotFound(url.getPath())
        );
    }

    @Override
    public FileLocationHolder upload(FileHolder  content, FileLocationHolder url) {
        FILES.put(url.getPath(), content);
        return new PathFileLocationHolder(url.getPath());
    }

}
