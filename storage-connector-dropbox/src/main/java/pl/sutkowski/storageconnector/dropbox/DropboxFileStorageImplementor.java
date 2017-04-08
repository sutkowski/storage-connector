package pl.sutkowski.storageconnector.dropbox;

import pl.sutkowski.api.FileLocationHolder;
import pl.sutkowski.api.FileStorageImplementor;

public interface DropboxFileStorageImplementor extends FileStorageImplementor {

    FileLocationHolder createFolder(FileLocationHolder url);

}
