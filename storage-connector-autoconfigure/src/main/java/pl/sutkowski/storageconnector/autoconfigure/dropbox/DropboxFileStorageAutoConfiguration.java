package pl.sutkowski.storageconnector.autoconfigure.dropbox;

import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.storageconnector.dropbox.impl.DropboxCredentialsProvider;
import pl.sutkowski.storageconnector.dropbox.DefaultDropboxFileStorage;
import pl.sutkowski.storageconnector.dropbox.impl.DropboxClient;
import pl.sutkowski.storageconnector.dropbox.impl.PropertiesDropboxCredentialsProvider;

@Configuration
@ConditionalOnClass(DefaultDropboxFileStorage.class)
@PropertySource(value = "file:/home/dropbox.yml", ignoreResourceNotFound = true)
public class DropboxFileStorageAutoConfiguration {

    @Bean
    public FileStorage fileStorage(DropboxClient dropboxClient) {
        return new DefaultDropboxFileStorage(dropboxClient);
    }

    @Bean
    public DropboxCredentialsProvider dropboxCredentialsProvider() {
        return new PropertiesDropboxCredentialsProvider();
    }
    @Bean
    public DropboxClient dropboxClient(DropboxCredentialsProvider dropboxCredentialsProvider) {
        return new DropboxClient(dropboxCredentialsProvider);
    }

    @Bean
    PlaceholderConfigurerSupport placeholderConfigurerSupport(){
        return new PropertySourcesPlaceholderConfigurer();
    }
}
