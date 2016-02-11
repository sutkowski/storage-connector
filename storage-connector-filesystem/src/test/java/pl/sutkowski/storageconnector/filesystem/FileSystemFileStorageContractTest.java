package pl.sutkowski.storageconnector.filesystem;

import pl.sutkowski.api.FileStorageProvider;
import pl.sutkowski.storageconnector.test.base.contract.TmpDataFileStorageContractTestBase;

public class FileSystemFileStorageContractTest extends TmpDataFileStorageContractTestBase {

    @Override
    public FileStorageProvider getFileStorageProvider() {
        return new FileSystemFileStorageProvider();
    }
}

