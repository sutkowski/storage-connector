package pl.sutkowski.storageconnector.filesystem;

import pl.sutkowski.api.FileStorageImplementor;
import pl.sutkowski.storageconnector.test.base.contract.FileStorageContractTestBase;

public class FileSystemFileStorageImplementorContractTest extends FileStorageContractTestBase {

    @Override
    public FileStorageImplementor getFileStorageImplementor(){
        return new FileSystemFileStorageImplementor("${user.home}/tmp/");
    }
}
