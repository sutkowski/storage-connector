package pl.sutkowski.storageconnector.amazons3;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.sutkowski.api.FileStorageImplementor;
import pl.sutkowski.storageconnector.test.base.performance.FileStoragePerformanceTestBase;


@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@SpringApplicationConfiguration(classes = {AmazonS3TestConfig.class})
public class AmazonS3FileStorageImplementorPerformanceTest extends FileStoragePerformanceTestBase {

    @Autowired
    FileStorageImplementor fileStorageImplementor;

    @Override
    public FileStorageImplementor getFileStorage(){
        return fileStorageImplementor;
    }
}