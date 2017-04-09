package pl.sutkowski.storageconnector.autoconfigure.googledrive;

import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.api.FileStorageImplementation;
import pl.sutkowski.api.FileStorageImplementor;
import pl.sutkowski.storageconnector.googledrive.DefaultGoogleDriveFileStorageImplementor;
import pl.sutkowski.storageconnector.googledrive.authorization.GoogleAuthorization;
import pl.sutkowski.storageconnector.googledrive.authorization.GoogleDriveCredentialsProvider;
import pl.sutkowski.storageconnector.googledrive.authorization.impl.StreamGoogleAuthorization;
import pl.sutkowski.storageconnector.googledrive.impl.GoogleDriveClient;

@Configuration
@ConditionalOnClass(DefaultGoogleDriveFileStorageImplementor.class)
@PropertySource(value = "file:${user.home}/googledrive.yml", ignoreResourceNotFound = true)
public class GoogleDriveFileStorageAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(FileStorageImplementor.class)
    public FileStorageImplementor fileStorageImplementor(GoogleDriveClient googleDriveClient) {
        return new DefaultGoogleDriveFileStorageImplementor(googleDriveClient);
    }

    @Bean
    @ConditionalOnMissingBean(FileStorage.class)
    public FileStorage fileStorage(FileStorageImplementor fileStorageImplementor) {
        return new FileStorageImplementation(fileStorageImplementor);
    }

    @Bean
    @ConditionalOnMissingBean(GoogleDriveCredentialsProvider.class)
    public GoogleDriveCredentialsProvider googleDriveCredentialsProvider() {
        return new PropertiesGoogleDriveCredentialsProvider();
    }

    @Bean
    @ConditionalOnMissingBean(GoogleAuthorization.class)
    public GoogleAuthorization googleAuthorization(GoogleDriveCredentialsProvider googleDriveCredentialsProvider) {
        return new StreamGoogleAuthorization(googleDriveCredentialsProvider);
    }

    @Bean
    @ConditionalOnMissingBean(GoogleDriveClient.class)
    public GoogleDriveClient googleDriveClient(GoogleAuthorization googleAuthorization) {
        return new GoogleDriveClient(googleAuthorization);
    }

    @Bean
    PlaceholderConfigurerSupport placeholderConfigurerSupport(){
        return new PropertySourcesPlaceholderConfigurer();
    }
}
