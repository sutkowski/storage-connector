package pl.sutkowski.storageconnector.autoconfigure.filesystem;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.api.FileStorageImplementation;
import pl.sutkowski.api.FileStorageImplementor;
import pl.sutkowski.api.exception.FileStorageInitializationException;
import pl.sutkowski.storageconnector.filesystem.FileSystemFileStorageImplementor;

@Configuration
@ConditionalOnClass(FileSystemFileStorageImplementor.class)
public class FileSystemFileStorageAutoConfiguration {

    @Value("${storage.connector.filestorage.basedirectory:}")
    private String baseDirectory;

    @Bean
    @ConditionalOnMissingBean(FileStorageImplementor.class)
    public FileStorageImplementor fileStorageImplementor() {
        if(baseDirectory.isEmpty()) {
            throw new FileStorageInitializationException("Base directory must be set");
        }
        return new FileSystemFileStorageImplementor(baseDirectory);
    }

    @Bean
    @ConditionalOnMissingBean(FileStorage.class)
    public FileStorage fileStorage(FileStorageImplementor fileStorageImplementor) {
        return new FileStorageImplementation(fileStorageImplementor);
    }
}
