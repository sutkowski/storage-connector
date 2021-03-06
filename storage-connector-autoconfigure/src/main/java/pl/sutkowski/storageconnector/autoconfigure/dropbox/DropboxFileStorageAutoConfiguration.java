package pl.sutkowski.storageconnector.autoconfigure.dropbox;

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
import pl.sutkowski.storageconnector.dropbox.DefaultDropboxFileStorageImplementor;
import pl.sutkowski.storageconnector.dropbox.impl.DropboxClient;
import pl.sutkowski.storageconnector.dropbox.impl.DropboxCredentialsProvider;

@Configuration
@ConditionalOnClass(DefaultDropboxFileStorageImplementor.class)
@PropertySource(value = "file:${user.home}/dropbox.yml", ignoreResourceNotFound = true)
public class DropboxFileStorageAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(FileStorageImplementor.class)
    public FileStorageImplementor fileStorageImplementor(DropboxClient dropboxClient) {
        return new DefaultDropboxFileStorageImplementor(dropboxClient);
    }

    @Bean
    @ConditionalOnMissingBean(FileStorage.class)
    public FileStorage fileStorage(FileStorageImplementor fileStorageImplementor) {
        return new FileStorageImplementation(fileStorageImplementor);
    }

    @Bean
    @ConditionalOnMissingBean(DropboxCredentialsProvider.class)
    public DropboxCredentialsProvider dropboxCredentialsProvider() {
        return new PropertiesDropboxCredentialsProvider();
    }

    @Bean
    @ConditionalOnMissingBean(DropboxClient.class)
    public DropboxClient dropboxClient(DropboxCredentialsProvider dropboxCredentialsProvider) {
        return new DropboxClient(dropboxCredentialsProvider);
    }

    @Bean
    PlaceholderConfigurerSupport placeholderConfigurerSupport(){
        return new PropertySourcesPlaceholderConfigurer();
    }
}
