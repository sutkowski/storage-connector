package pl.sutkowski.api;

import java.io.IOException;

public interface FileStorageProvider {

    FileStorage getFileStorage() throws IOException;
}
