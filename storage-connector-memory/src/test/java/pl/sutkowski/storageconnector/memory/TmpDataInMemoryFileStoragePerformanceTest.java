package pl.sutkowski.storageconnector.memory;

import pl.sutkowski.storageconnector.test.utils.ContentProvider;
import pl.sutkowski.storageconnector.test.utils.TmpDataContentProvider;

public class TmpDataInMemoryFileStoragePerformanceTest extends AbstractInMemoryFileStoragePerformanceTest {

    @Override
    public ContentProvider getContentProvider() {
        return new TmpDataContentProvider();
    }
}
