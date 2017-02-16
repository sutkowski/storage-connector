package pl.sutkowski.storageconnector.googledrive.impl;

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
@SpringApplicationConfiguration(classes = {GoogleDriveTestConfig.class})
public class GoogleDriveClientTest  extends FileStorageContractTestBase {

    @Autowired
    FileStorage googleDriveFileStorage;

    @Override
    public FileStorageProvider getFileStorageProvider() {
        return () -> googleDriveFileStorage;
    }
}


