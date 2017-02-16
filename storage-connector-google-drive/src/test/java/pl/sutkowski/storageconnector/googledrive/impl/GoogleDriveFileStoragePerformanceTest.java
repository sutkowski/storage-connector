package pl.sutkowski.storageconnector.googledrive.impl;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.api.FileStorageProvider;
import pl.sutkowski.storageconnector.googledrive.DefaultGoogleDriveFileStorage;
import pl.sutkowski.storageconnector.googledrive.authorization.GoogleAuthorization;
import pl.sutkowski.storageconnector.googledrive.authorization.GoogleDriveCredentialsProvider;
import pl.sutkowski.storageconnector.googledrive.authorization.impl.PropertiesGoogleDriveCredentialsProvider;
import pl.sutkowski.storageconnector.googledrive.authorization.impl.StreamGoogleAuthorization;
import pl.sutkowski.storageconnector.test.base.contract.FileStorageContractTestBase;
import pl.sutkowski.storageconnector.test.base.performance.FileStoragePerformanceTestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@SpringApplicationConfiguration(classes = {GoogleDriveTestConfig.class})
public class GoogleDriveFileStoragePerformanceTest extends FileStoragePerformanceTestBase {

    @Autowired
    FileStorage googleDriveFileStorage;

    @Override
    protected FileStorageProvider getFileStorageProvider() {
        return () -> googleDriveFileStorage;
    }

}
