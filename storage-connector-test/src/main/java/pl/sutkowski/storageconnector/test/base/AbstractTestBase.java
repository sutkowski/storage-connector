package pl.sutkowski.storageconnector.test.base;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import pl.sutkowski.api.FileStorageImplementor;

public abstract class AbstractTestBase {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public abstract FileStorageImplementor getFileStorage();

}
