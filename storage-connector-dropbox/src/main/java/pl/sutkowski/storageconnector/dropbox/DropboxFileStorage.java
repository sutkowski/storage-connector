package pl.sutkowski.storageconnector.dropbox;

import pl.sutkowski.api.FileStorage;

import java.nio.file.Path;

public interface DropboxFileStorage extends FileStorage {

    Path createFolder(Path url);

    void move(Path fromPath, Path toPath);
}
