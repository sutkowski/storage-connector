package pl.sutkowski.storageconnector.memory;

import pl.sutkowski.api.FileStorage;
import pl.sutkowski.api.FileStorageProvider;

public final class InMemoryFileStorageProvider
        implements FileStorageProvider {

    @Override
    public FileStorage getFileStorage() {
        return new InMemoryFileStorage();
    }

}
