package memory;


import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.storageconnector.memory.InMemoryFileStorage;
import sample.filesystem.SampleInMemoryFileStorageApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleInMemoryFileStorageApplication.class)
public class SampleInMemoryFileStorageRestControllerTest {

    @Autowired
    FileStorage fileStorage;

    @Test
    public void contextLoads() {
    }

    @Test
    public void fileStorageShouldBeInstanceOfInMemory() {
        Assertions.assertThat(fileStorage).isNotNull();
        Assertions.assertThat(fileStorage instanceof InMemoryFileStorage).isTrue();
    }

}