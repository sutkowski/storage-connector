package pl.sutkowski.storageconnector.autoconfigure.amazons3;

import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.storageconnector.amazons3.DefaultAmazonS3FileStorage;
import pl.sutkowski.storageconnector.dropbox.DefaultDropboxFileStorage;
import pl.sutkowski.storageconnector.dropbox.impl.DropboxClient;
import pl.sutkowski.storageconnector.dropbox.impl.DropboxCredentialsProvider;
import pl.sutkowski.storageconnector.dropbox.impl.PropertiesDropboxCredentialsProvider;

@Configuration
@ConditionalOnClass(DefaultDropboxFileStorage.class)
@PropertySource(value = "file:${user.home}/dropbox.yml", ignoreResourceNotFound = true)
public class AmazonS3FileStorageAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(FileStorage.class)
    public FileStorage fileStorage() {
        return new DefaultAmazonS3FileStorage();
    }

    @Bean
    PlaceholderConfigurerSupport placeholderConfigurerSupport(){
        return new PropertySourcesPlaceholderConfigurer();
    }
}
