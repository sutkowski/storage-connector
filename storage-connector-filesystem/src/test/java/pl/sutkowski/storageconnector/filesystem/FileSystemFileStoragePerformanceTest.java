package pl.sutkowski.storageconnector.filesystem;

import pl.sutkowski.api.FileStorageProvider;
import pl.sutkowski.storageconnector.test.base.performance.FileStoragePerformanceTestBase;

public class FileSystemFileStoragePerformanceTest extends FileStoragePerformanceTestBase {

    @Override
    public FileStorageProvider getFileStorageProvider() {
        return new FileSystemFileStorageProvider();
    }
}
