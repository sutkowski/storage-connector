package pl.sutkowski.api;

import java.nio.file.Path;

/**
 *
 * @author eS
 */
public interface FileStorage {

    byte[] download(Path url);

    void remove(Path url);

    Path upload(byte[] content);
}
