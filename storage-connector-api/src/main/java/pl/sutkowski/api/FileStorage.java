package pl.sutkowski.api;

import java.nio.file.Path;

public interface FileStorage {

    Path upload(byte[] content);

    byte[] download(Path url);

    void remove(Path url);
}
