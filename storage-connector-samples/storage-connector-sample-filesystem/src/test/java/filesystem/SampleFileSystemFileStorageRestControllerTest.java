package filesystem;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.storageconnector.filesystem.FileSystemFileStorage;
import sample.filesystem.SampleFileSystemFileStorageApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleFileSystemFileStorageApplication.class)
@TestPropertySource("classpath:application.yml")
public class SampleFileSystemFileStorageRestControllerTest {

    @Autowired
    FileStorage fileStorage;

    @Test
    public void contextLoads() {
    }

    @Test
    public void fileStorageShouldBeInstanceOfFileSystem() {
        Assertions.assertThat(fileStorage).isNotNull();
        Assertions.assertThat(fileStorage instanceof FileSystemFileStorage).isTrue();
    }

}