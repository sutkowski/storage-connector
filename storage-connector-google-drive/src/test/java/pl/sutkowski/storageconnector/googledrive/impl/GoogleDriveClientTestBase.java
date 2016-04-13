package pl.sutkowski.storageconnector.googledrive.impl;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.storageconnector.googledrive.GoogleDriveFileStorage;
import pl.sutkowski.storageconnector.test.base.contract.TmpDataFileStorageContractTestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@SpringApplicationConfiguration(classes = {GoogleDriveClientTestBase.TestConfig.class})
public abstract class GoogleDriveClientTestBase extends TmpDataFileStorageContractTestBase {

    @Configuration
    @PropertySource(value = "file:${user.home}/googledrive.yml", ignoreResourceNotFound = false)
    @ComponentScan(basePackages = "pl.sutkowski.storageconnector.googledrive")
    public static class TestConfig {

        @Bean
        PlaceholderConfigurerSupport placeholderConfigurerSupport(){
            return new PropertySourcesPlaceholderConfigurer();
        }

        @Bean
        public FileStorage fileStorage(GoogleDriveClient googleDriveClient) {
            return new GoogleDriveFileStorage(googleDriveClient);
        }

        @Bean
        public GoogleDriveClient googleDriveClient(GoogleDriveCredentialsProvider googleDriveCredentialsProvider) {
            return new GoogleDriveClient(googleDriveCredentialsProvider);
        }
        @Bean
        public GoogleDriveCredentialsProvider googleDriveCredentialsProvider(){
            return new PropertiesGoogleDriveCredentialsProvider();
        }
    }
}
