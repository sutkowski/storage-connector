package pl.sutkowski.storageconnector.memory;

import pl.sutkowski.api.FileStorageImplementor;
import pl.sutkowski.storageconnector.test.base.contract.FileStorageContractTestBase;

public class InMemoryFileStorageImplementorContractTest extends FileStorageContractTestBase {

    @Override
    public FileStorageImplementor getFileStorageImplementor(){
        return new InMemoryFileStorageImplementor();
    }
}
