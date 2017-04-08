package pl.sutkowski.storageconnector.memory;

import pl.sutkowski.api.FileStorageImplementor;
import pl.sutkowski.storageconnector.test.base.contract.FileStorageContractTestBase;

public class InMemoryFileStorageImplementorContractTest extends FileStorageContractTestBase {

    @Override
    public FileStorageImplementor getFileStorage(){
        return new InMemoryFileStorageImplementor();
    }
}
