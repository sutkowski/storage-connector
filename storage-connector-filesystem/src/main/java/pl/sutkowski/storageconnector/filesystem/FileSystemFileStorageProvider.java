package pl.sutkowski.storageconnector.filesystem;

import pl.sutkowski.api.FileStorage;
import pl.sutkowski.api.FileStorageProvider;

public final class FileSystemFileStorageProvider
        implements FileStorageProvider {

    @Override
    public FileStorage getFileStorage() {
        return new FileSystemFileStorage("${user.home}/tmp/");
    }

}
