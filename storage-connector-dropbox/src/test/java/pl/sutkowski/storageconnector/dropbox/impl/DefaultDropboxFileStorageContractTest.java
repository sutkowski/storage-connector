package pl.sutkowski.storageconnector.dropbox.impl;

import org.springframework.beans.factory.annotation.Autowired;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.api.FileStorageProvider;

public class DefaultDropboxFileStorageContractTest extends DropboxClientTestBase {

    @Autowired
    FileStorage fileStorage;

    @Override
    public FileStorageProvider getFileStorageProvider() {
        return () -> fileStorage;
    }

}
