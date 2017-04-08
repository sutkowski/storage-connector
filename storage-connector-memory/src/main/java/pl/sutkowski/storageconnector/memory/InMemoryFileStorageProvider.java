package pl.sutkowski.storageconnector.memory;

import pl.sutkowski.api.FileStorageImplementor;
import pl.sutkowski.api.FileStorageProvider;

public final class InMemoryFileStorageProvider
        implements FileStorageProvider {

    @Override
    public FileStorageImplementor getFileStorage() {
        return new InMemoryFileStorageImplementor();
    }

}
