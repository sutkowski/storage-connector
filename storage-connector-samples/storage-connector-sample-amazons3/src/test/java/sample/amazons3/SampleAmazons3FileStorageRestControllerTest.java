package sample.amazons3;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.storageconnector.amazons3.Amazons3FileStorage;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleAmazons3FileStorageApplication.class)
public class SampleAmazons3FileStorageRestControllerTest {

    @Autowired
    FileStorage fileStorage;

    @Test
    public void contextLoads() {
    }

    @Test
    public void fileStorageShouldBeInstanceOfAmazons3() {
        Assertions.assertThat(fileStorage).isNotNull();
        Assertions.assertThat(fileStorage instanceof Amazons3FileStorage).isTrue();
    }

}