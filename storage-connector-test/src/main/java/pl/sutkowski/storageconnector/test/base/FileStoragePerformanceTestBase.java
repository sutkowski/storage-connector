package pl.sutkowski.storageconnector.test.base;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sutkowski.api.FileHolder;

public abstract class FileStoragePerformanceTestBase extends AbstractTestBase{

    Logger log = LoggerFactory.getLogger(FileStoragePerformanceTestBase.class);

    @Rule
    public Stopwatch stopwatch = new Stopwatch(){
        @Override
        protected void succeeded(long nanos, Description description) {
            log.error(getTestLogDescription(description, nanos));
            super.succeeded(nanos, description);
        }
    };

    @Test
    public void shouldUploadFile() throws Exception{
        getFileStorage().upload(getContent());
    }

    private FileHolder getContent() {
        return getContentProvider().getContent();
    }

}
