package pl.sutkowski.storageconnector.dropbox;

import pl.sutkowski.api.FileLocationHolder;
import pl.sutkowski.api.FileStorage;

public interface DropboxFileStorage extends FileStorage {

    FileLocationHolder createFolder(FileLocationHolder url);

}
