package pl.sutkowski.storageconnector.autoconfigure.dropbox;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.storageconnector.dropbox.DropboxCredentialsProvider;
import pl.sutkowski.storageconnector.dropbox.DropboxFileStorage;
import pl.sutkowski.storageconnector.dropbox.impl.PropertiesDropboxCredentialsProvider;

@Configuration
@ConditionalOnClass(DropboxFileStorage.class)
@PropertySource(value = "/home/dropbox.yml", ignoreResourceNotFound = true)
public class DropboxFileStorageAutoConfiguration {

    @Bean
    public FileStorage fileStorage() {
        return new DropboxFileStorage();
    }

    @Bean
    public DropboxCredentialsProvider dropboxCredentialsProvider() {
        return new PropertiesDropboxCredentialsProvider();
    }
}
