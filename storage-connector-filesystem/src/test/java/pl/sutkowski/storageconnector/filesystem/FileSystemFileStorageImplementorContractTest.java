package pl.sutkowski.storageconnector.filesystem;

import pl.sutkowski.api.FileStorageProvider;
import pl.sutkowski.storageconnector.test.base.contract.FileStorageContractTestBase;

public class FileSystemFileStorageImplementorContractTest extends FileStorageContractTestBase {

    @Override
    public FileStorageProvider getFileStorageProvider() {
        return new FileSystemFileStorageProvider();
    }
}
