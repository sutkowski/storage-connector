package pl.sutkowski.storageconnector.dropbox.impl;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.sutkowski.api.FileStorageImplementor;
import pl.sutkowski.storageconnector.test.base.contract.FileStorageContractTestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@SpringApplicationConfiguration(classes = {DropboxTestConfig.class})
public class DefaultDropboxFileStorageImplementorContractTest extends FileStorageContractTestBase {

    @Autowired
    FileStorageImplementor fileStorageImplementor;

    @Override
    public FileStorageImplementor getFileStorage(){
        return fileStorageImplementor;
    }
}
