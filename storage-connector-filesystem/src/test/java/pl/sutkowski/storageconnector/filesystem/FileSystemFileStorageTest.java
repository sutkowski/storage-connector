package pl.sutkowski.storageconnector.filesystem;

import pl.sutkowski.api.FileStorageProvider;
import pl.sutkowski.storageconnector.test.base.TmpDataFileStorageTestBase;

public class FileSystemFileStorageTest extends TmpDataFileStorageTestBase {

    @Override
    public FileStorageProvider getFileStorageProvider() {
        return new FileSystemFileStorageProvider();
    }
}

