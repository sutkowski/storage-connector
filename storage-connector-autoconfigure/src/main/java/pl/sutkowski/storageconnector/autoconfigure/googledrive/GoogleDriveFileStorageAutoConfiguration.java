package pl.sutkowski.storageconnector.autoconfigure.googledrive;

import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.storageconnector.dropbox.DefaultDropboxFileStorage;
import pl.sutkowski.storageconnector.dropbox.impl.DropboxClient;
import pl.sutkowski.storageconnector.dropbox.impl.DropboxCredentialsProvider;
import pl.sutkowski.storageconnector.dropbox.impl.PropertiesDropboxCredentialsProvider;
import pl.sutkowski.storageconnector.googledrive.GoogleDriveFileStorage;
import pl.sutkowski.storageconnector.googledrive.authorization.GoogleAuthorization;
import pl.sutkowski.storageconnector.googledrive.authorization.GoogleDriveCredentialsProvider;
import pl.sutkowski.storageconnector.googledrive.authorization.impl.PropertiesGoogleDriveCredentialsProvider;
import pl.sutkowski.storageconnector.googledrive.authorization.impl.StreamGoogleAuthorization;
import pl.sutkowski.storageconnector.googledrive.impl.GoogleDriveClient;

@Configuration
@ConditionalOnClass(DefaultDropboxFileStorage.class)
@PropertySource(value = "file:${user.home}/googledrive.yml", ignoreResourceNotFound = true)
public class GoogleDriveFileStorageAutoConfiguration {

    @Bean
    public FileStorage fileStorage(GoogleDriveClient googleDriveClient) {
        return new GoogleDriveFileStorage(googleDriveClient);
    }

    @Bean
    public GoogleDriveCredentialsProvider googleDriveCredentialsProvider() {
        return new PropertiesGoogleDriveCredentialsProvider();
    }

    @Bean
    public GoogleAuthorization googleAuthorization(GoogleDriveCredentialsProvider googleDriveCredentialsProvider) {
        return new StreamGoogleAuthorization(googleDriveCredentialsProvider);
    }

    @Bean
    public GoogleDriveClient googleDriveClient(GoogleAuthorization googleAuthorization) {
        return new GoogleDriveClient(googleAuthorization);
    }

    @Bean
    PlaceholderConfigurerSupport placeholderConfigurerSupport(){
        return new PropertySourcesPlaceholderConfigurer();
    }
}
