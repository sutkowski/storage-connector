package pl.sutkowski.storageconnector.filesystem;

import pl.sutkowski.api.FileStorageImplementor;
import pl.sutkowski.api.FileStorageProvider;

public final class FileSystemFileStorageProvider
        implements FileStorageProvider {

    @Override
    public FileStorageImplementor getFileStorage() {
        return new FileSystemFileStorageImplementor("${user.home}/tmp/");
    }

}
