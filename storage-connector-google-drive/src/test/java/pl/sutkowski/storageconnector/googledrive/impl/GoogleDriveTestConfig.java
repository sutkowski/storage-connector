package pl.sutkowski.storageconnector.googledrive.impl;

import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import pl.sutkowski.api.FileStorageImplementor;
import pl.sutkowski.storageconnector.googledrive.DefaultGoogleDriveFileStorageImplementor;
import pl.sutkowski.storageconnector.googledrive.authorization.GoogleAuthorization;
import pl.sutkowski.storageconnector.googledrive.authorization.GoogleDriveCredentialsProvider;
import pl.sutkowski.storageconnector.googledrive.authorization.impl.StreamGoogleAuthorization;


@Configuration
@PropertySource(value = "file:${user.home}/googledrive.yml", ignoreResourceNotFound = false)
@ComponentScan(basePackages = "pl.sutkowski.storageconnector.googledrive")
public class GoogleDriveTestConfig {

    @Bean
    PlaceholderConfigurerSupport placeholderConfigurerSupport() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public FileStorageImplementor fileStorage(GoogleDriveClient googleDriveClient) {
        return new DefaultGoogleDriveFileStorageImplementor(googleDriveClient);
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
    public GoogleDriveCredentialsProvider googleDriveCredentialsProvider() {
        return new TestGoogleDriveCredentialsProvider();
    }
}
