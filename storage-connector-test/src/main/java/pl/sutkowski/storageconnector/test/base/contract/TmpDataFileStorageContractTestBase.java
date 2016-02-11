package pl.sutkowski.storageconnector.test.base.contract;

import pl.sutkowski.storageconnector.test.utils.ContentProvider;
import pl.sutkowski.storageconnector.test.utils.TmpDataContentProvider;

/**
 * @author eS
 */
public abstract class TmpDataFileStorageContractTestBase
        extends FileStorageContractTestBase {

    @Override
    public ContentProvider getContentProvider() {
        return new TmpDataContentProvider();
    }
}
