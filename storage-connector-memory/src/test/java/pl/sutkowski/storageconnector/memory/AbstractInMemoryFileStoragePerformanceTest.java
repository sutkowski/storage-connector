package pl.sutkowski.storageconnector.memory;

import pl.sutkowski.api.FileStorageProvider;
import pl.sutkowski.storageconnector.test.base.FileStoragePerformanceTestBase;

public abstract class AbstractInMemoryFileStoragePerformanceTest extends FileStoragePerformanceTestBase {

    @Override
    public FileStorageProvider getFileStorageProvider() {
        return new InMemoryFileStorageProvider();
    }

}
