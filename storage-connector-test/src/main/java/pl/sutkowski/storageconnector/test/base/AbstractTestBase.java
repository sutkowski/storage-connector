package pl.sutkowski.storageconnector.test.base;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.api.FileStorageProvider;

public abstract class AbstractTestBase {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    protected abstract FileStorageProvider getFileStorageProvider();

    protected FileStorage getFileStorage(){
        return getFileStorageProvider().getFileStorage();
    }

}
