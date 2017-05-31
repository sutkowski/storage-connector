package sample.amazons3;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.sutkowski.api.FileStorageImplementor;
import pl.sutkowski.storageconnector.amazons3.AmazonS3FileStorageImplementor;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleAmazons3FileStorageApplication.class)
public class SampleAmazons3FileStorageImplementorRestControllerTest {

    @Autowired
    FileStorageImplementor fileStorageImplementor;

    @Test
    public void contextLoads() {
    }

    @Test
    public void fileStorageShouldBeInstanceOfAmazons3() {
        Assertions.assertThat(fileStorageImplementor).isNotNull();
        Assertions.assertThat(fileStorageImplementor instanceof AmazonS3FileStorageImplementor).isTrue();
    }

}