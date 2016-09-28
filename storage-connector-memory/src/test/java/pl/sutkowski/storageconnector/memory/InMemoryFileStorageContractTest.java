package pl.sutkowski.storageconnector.memory;

import pl.sutkowski.api.FileStorageProvider;
import pl.sutkowski.storageconnector.test.base.contract.FileStorageContractTestBase;

public class InMemoryFileStorageContractTest extends FileStorageContractTestBase {

    @Override
    public FileStorageProvider getFileStorageProvider() {
        return new InMemoryFileStorageProvider();
    }

}
