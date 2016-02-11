package pl.sutkowski.storageconnector.test.base;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.Description;
import pl.sutkowski.api.FileStorageProvider;
import pl.sutkowski.storageconnector.test.utils.ContentProvider;

import java.util.concurrent.TimeUnit;

public abstract class AbstractTestBase {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public abstract ContentProvider getContentProvider();

    public abstract FileStorageProvider getFileStorageProvider();

    public String getTestLogDescription(Description description, Long timeNanos) {
        return String.format("\nTest %s executed for : \n " +
                "ContentProvider of type : %s \n " +
                "FileStorageProvider of type : %s \n" +
                "Executed in %s nanoseconds (%s seconds)\n",
                description.getMethodName(),
                getContentProvider().getClass().getCanonicalName(),
                getFileStorageProvider().getClass().getCanonicalName(),
                timeNanos,
                TimeUnit.NANOSECONDS.toSeconds(timeNanos));
    }
}
