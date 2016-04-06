package pl.sutkowski.storageconnector.filesystem;

import java.io.IOException;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.api.FileStorageProvider;

public final class FileSystemFileStorageProvider
        implements FileStorageProvider {

    @Override
    public FileStorage getFileStorage() throws IOException {
        return new FileSystemFileStorage("${user.home}/tmp/");
    }

}
