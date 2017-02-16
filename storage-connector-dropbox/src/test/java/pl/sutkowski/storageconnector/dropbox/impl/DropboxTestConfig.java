package pl.sutkowski.storageconnector.dropbox.impl;

import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.storageconnector.dropbox.DefaultDropboxFileStorage;

@Configuration
@PropertySource(value = "file:${user.home}/dropbox.yml", ignoreResourceNotFound = false)
@ComponentScan(basePackages = "pl.sutkowski.storageconnector.dropbox")
public class DropboxTestConfig {

    @Bean
    PlaceholderConfigurerSupport placeholderConfigurerSupport() {
        return new PropertySourcesPlaceholderConfigurer();
    }

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
}

