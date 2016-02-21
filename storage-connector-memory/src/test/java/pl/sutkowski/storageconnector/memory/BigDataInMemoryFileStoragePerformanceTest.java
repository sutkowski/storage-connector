package pl.sutkowski.storageconnector.memory;

import pl.sutkowski.storageconnector.test.utils.BigDataContentProvider;
import pl.sutkowski.storageconnector.test.utils.ContentProvider;

public class BigDataInMemoryFileStoragePerformanceTest extends AbstractInMemoryFileStoragePerformanceTest {

    @Override
    public ContentProvider getContentProvider() {
        return new BigDataContentProvider();
    }
}
