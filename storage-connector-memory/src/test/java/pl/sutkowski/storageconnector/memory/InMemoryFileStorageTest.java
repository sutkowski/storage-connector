package pl.sutkowski.storageconnector.memory;

import pl.sutkowski.api.FileStorageProvider;
import pl.sutkowski.storageconnector.test.base.TmpDataFileStorageTestBase;

/**
 * @author eS
 */
public class InMemoryFileStorageTest extends TmpDataFileStorageTestBase {

    @Override
    public FileStorageProvider getFileStorageProvider() {
        return new InMemoryFileStorageProvider();
    }

}
