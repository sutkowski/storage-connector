package pl.sutkowski.storageconnector.memory;

import pl.sutkowski.api.FileStorageProvider;
import pl.sutkowski.storageconnector.test.base.performance.FileStoragePerformanceTestBase;

public class InMemoryFileStorageImplementorPerformanceTest extends FileStoragePerformanceTestBase {

    @Override
    public FileStorageProvider getFileStorageProvider() {
        return new InMemoryFileStorageProvider();
    }
}
