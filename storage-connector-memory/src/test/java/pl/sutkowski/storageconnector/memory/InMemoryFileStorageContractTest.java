package pl.sutkowski.storageconnector.memory;

import pl.sutkowski.api.FileStorageProvider;
import pl.sutkowski.storageconnector.test.base.contract.TmpDataFileStorageContractTestBase;

public class InMemoryFileStorageContractTest extends TmpDataFileStorageContractTestBase {

    @Override
    public FileStorageProvider getFileStorageProvider() {
        return new InMemoryFileStorageProvider();
    }

}
