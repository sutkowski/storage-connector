package pl.sutkowski.storageconnector.filesystem;

import pl.sutkowski.api.FileStorageImplementor;
import pl.sutkowski.storageconnector.test.base.performance.FileStoragePerformanceTestBase;

public class FileSystemFileStorageImplementorPerformanceTest extends FileStoragePerformanceTestBase {

    @Override
    public FileStorageImplementor getFileStorageImplementor(){
        return new FileSystemFileStorageImplementor("${user.home}/tmp/");
    }
}
