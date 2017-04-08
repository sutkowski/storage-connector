package sample.dropbox;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.sutkowski.api.FileStorageImplementor;
import pl.sutkowski.storageconnector.dropbox.DropboxFileStorageImplementor;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleDropboxFileStorageApplication.class)
public class SampleDropboxFileStorageImplementorRestControllerTest {

    @Autowired
    FileStorageImplementor fileStorageImplementor;

    @Test
    public void contextLoads() {
    }

    @Test
    public void fileStorageShouldBeInstanceOfDropbox() {
        Assertions.assertThat(fileStorageImplementor).isNotNull();
        Assertions.assertThat(fileStorageImplementor instanceof DropboxFileStorageImplementor).isTrue();
    }

}