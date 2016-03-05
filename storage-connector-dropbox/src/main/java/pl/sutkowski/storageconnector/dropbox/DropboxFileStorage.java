package pl.sutkowski.storageconnector.dropbox;

import pl.sutkowski.api.FileStorage;

import java.nio.file.Path;

public class DropboxFileStorage implements FileStorage {

    @Override
    public byte[] download(Path url) {
        return new byte[0];
    }

    @Override
    public void remove(Path url) {

    }

    @Override
    public Path upload(byte[] content) {
        return null;
    }
}
