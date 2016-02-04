package pl.sutkowski.storageconnector.test.base;

import pl.sutkowski.storageconnector.test.ContentProvider;
import pl.sutkowski.storageconnector.test.TmpDataContentProvider;

/**
 * @author eS
 */
public abstract class TmpDataFileStorageTestBase
        extends FileStorageTestBase {

    @Override
    public ContentProvider getContentProvider() {
        return new TmpDataContentProvider();
    }
}
