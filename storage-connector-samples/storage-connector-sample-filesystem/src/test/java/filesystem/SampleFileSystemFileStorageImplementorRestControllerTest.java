package filesystem;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.sutkowski.api.FileStorageImplementor;
import pl.sutkowski.storageconnector.filesystem.FileSystemFileStorageImplementor;
import sample.filesystem.SampleFileSystemFileStorageApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleFileSystemFileStorageApplication.class)
@TestPropertySource("classpath:application.yml")
public class SampleFileSystemFileStorageImplementorRestControllerTest {

    @Autowired
    FileStorageImplementor fileStorageImplementor;

    @Test
    public void contextLoads() {
    }

    @Test
    public void fileStorageShouldBeInstanceOfFileSystem() {
        Assertions.assertThat(fileStorageImplementor).isNotNull();
        Assertions.assertThat(fileStorageImplementor instanceof FileSystemFileStorageImplementor).isTrue();
    }

}