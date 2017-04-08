package pl.sutkowski.storageconnector.dropbox.impl;

import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import pl.sutkowski.api.FileStorageImplementor;
import pl.sutkowski.storageconnector.dropbox.DefaultDropboxFileStorageImplementor;

@Configuration
@PropertySource(value = "file:${user.home}/dropbox.yml", ignoreResourceNotFound = false)
@ComponentScan(basePackages = "pl.sutkowski.storageconnector.dropbox")
public class DropboxTestConfig {

    @Bean
    PlaceholderConfigurerSupport placeholderConfigurerSupport() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public FileStorageImplementor fileStorage(DropboxClient dropboxClient) {
        return new DefaultDropboxFileStorageImplementor(dropboxClient);
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

