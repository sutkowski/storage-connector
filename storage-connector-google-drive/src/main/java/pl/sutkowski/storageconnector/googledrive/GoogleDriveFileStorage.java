package pl.sutkowski.storageconnector.googledrive;

import java.nio.file.Path;
import pl.sutkowski.api.FileStorage;

public class GoogleDriveFileStorage implements FileStorage {

    @Override
    public byte[] download(Path url) {
        return new byte[0];
    }

    @Override
    public void remove(Path url) {

    }

    @Override
    public Path upload(byte[] content, Path url) {
        return null;
    }
}
