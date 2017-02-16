package pl.sutkowski.storageconnector.amazons3;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.api.FileStorageProvider;
import pl.sutkowski.storageconnector.test.base.contract.FileStorageContractTestBase;


@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@SpringApplicationConfiguration(classes = {AmazonS3TestConfig.class})
public class DefaultAmazonS3FileStorageTest extends FileStorageContractTestBase {

    @Autowired
    FileStorage fileStorage;

    @Override
    public FileStorageProvider getFileStorageProvider() {
        return () -> fileStorage;
    }

}