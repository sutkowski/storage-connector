package pl.sutkowski.api;

import java.io.IOException;

/**
 *
 * @author eS
 */
public interface FileStorageProvider {

    FileStorage getFileStorage() throws IOException;
}
