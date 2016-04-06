package sample.dropbox;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.storageconnector.dropbox.DropboxFileStorage;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleDropboxFileStorageApplication.class)
public class SampleDropboxFileStorageRestControllerTest {

    @Autowired
    FileStorage fileStorage;

    @Test
    public void contextLoads() {
    }

    @Test
    public void fileStorageShouldBeInstanceOfDropbox() {
        Assertions.assertThat(fileStorage).isNotNull();
        Assertions.assertThat(fileStorage instanceof DropboxFileStorage).isTrue();
    }

}