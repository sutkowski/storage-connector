package pl.sutkowski.storageconnector.amazons3;

import pl.sutkowski.api.FileHolder;
import pl.sutkowski.api.FileLocationHolder;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.api.impl.ByteFileHolder;

public class AmazonS3FileStorage implements FileStorage {

    @Override
    public FileHolder download(FileLocationHolder url) {
        return new ByteFileHolder(new byte[0]);
    }

    @Override
    public void remove(FileLocationHolder url) {

    }

    @Override
    public FileLocationHolder upload(FileHolder content, FileLocationHolder url) {
        return null;
    }
}
