package pl.sutkowski.storageconnector.googledrive.impl;

import org.springframework.beans.factory.annotation.Autowired;
import pl.sutkowski.api.FileStorageProvider;
import pl.sutkowski.storageconnector.googledrive.GoogleDriveFileStorage;

public class GoogleDriveClientTest  extends GoogleDriveClientTestBase {

    @Autowired
    GoogleDriveFileStorage googleDriveFileStorage;

    @Override
    public FileStorageProvider getFileStorageProvider() {
        return () -> googleDriveFileStorage;
    }
}


