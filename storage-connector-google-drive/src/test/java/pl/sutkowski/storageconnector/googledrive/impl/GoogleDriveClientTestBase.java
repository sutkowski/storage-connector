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
import pl.sutkowski.storageconnector.googledrive.DefaultGoogleDriveFileStorage;
import pl.sutkowski.storageconnector.googledrive.authorization.GoogleAuthorization;
import pl.sutkowski.storageconnector.googledrive.authorization.GoogleDriveCredentialsProvider;
import pl.sutkowski.storageconnector.googledrive.authorization.impl.PropertiesGoogleDriveCredentialsProvider;
import pl.sutkowski.storageconnector.googledrive.authorization.impl.StreamGoogleAuthorization;
import pl.sutkowski.storageconnector.test.base.contract.FileStorageContractTestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@SpringApplicationConfiguration(classes = {GoogleDriveClientTestBase.TestConfig.class})
public abstract class GoogleDriveClientTestBase extends FileStorageContractTestBase {

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
            return new DefaultGoogleDriveFileStorage(googleDriveClient);
        }

        @Bean
        public GoogleDriveClient googleDriveClient(GoogleAuthorization googleAuthorization) {
            return new GoogleDriveClient(googleAuthorization);
        }

        @Bean
        public GoogleAuthorization googleAuthorization(GoogleDriveCredentialsProvider googleDriveCredentialsProvider) {
            return new StreamGoogleAuthorization(googleDriveCredentialsProvider);
        }
        @Bean
        public GoogleDriveCredentialsProvider googleDriveCredentialsProvider(){
            return new PropertiesGoogleDriveCredentialsProvider();
        }
    }
}
