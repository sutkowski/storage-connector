package pl.sutkowski.storageconnector.amazons3;

import pl.sutkowski.api.FileStorage;

import java.nio.file.Path;

public class AmazonS3FileStorage implements FileStorage {

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
