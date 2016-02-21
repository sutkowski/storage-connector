package pl.sutkowski.storageconnector.filesystem;

import pl.sutkowski.api.FileStorage;
import pl.sutkowski.api.FileStorageProvider;

import java.io.IOException;

public final class FileSystemFileStorageProvider
        implements FileStorageProvider {

    @Override
    public FileStorage getFileStorage() throws IOException {
        return new FileSystemFileStorage("/home/tmp/");
    }

}
