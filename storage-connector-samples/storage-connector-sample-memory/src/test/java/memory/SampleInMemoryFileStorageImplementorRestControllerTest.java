package memory;


import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.sutkowski.api.FileStorageImplementor;
import pl.sutkowski.storageconnector.memory.InMemoryFileStorageImplementor;
import sample.memory.SampleInMemoryFileStorageApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleInMemoryFileStorageApplication.class)
public class SampleInMemoryFileStorageImplementorRestControllerTest {

    @Autowired
    FileStorageImplementor fileStorageImplementor;

    @Test
    public void contextLoads() {
    }

    @Test
    public void fileStorageShouldBeInstanceOfInMemory() {
        Assertions.assertThat(fileStorageImplementor).isNotNull();
        Assertions.assertThat(fileStorageImplementor instanceof InMemoryFileStorageImplementor).isTrue();
    }

}