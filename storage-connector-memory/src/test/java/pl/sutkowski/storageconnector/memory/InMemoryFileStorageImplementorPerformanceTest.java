package pl.sutkowski.storageconnector.memory;

import pl.sutkowski.api.FileStorageImplementor;
import pl.sutkowski.storageconnector.test.base.performance.FileStoragePerformanceTestBase;

public class InMemoryFileStorageImplementorPerformanceTest extends FileStoragePerformanceTestBase {

    @Override
    public FileStorageImplementor getFileStorage(){
        return new InMemoryFileStorageImplementor();
    }
}
