package pl.sutkowski.storageconnector.autoconfigure.filesystem;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.api.exception.FileStorageInitializationException;
import pl.sutkowski.storageconnector.filesystem.FileSystemFileStorage;

@Configuration
@ConditionalOnClass(FileSystemFileStorage.class)
public class FileSystemFileStorageAutoConfiguration {

    @Value("${storage.connector.filestorage.basedirectory:}")
    private String baseDirectory;

    @Bean
    @ConditionalOnMissingBean(FileStorage.class)
    public FileStorage fileStorage() {
        if(baseDirectory.isEmpty()) {
            throw new FileStorageInitializationException("Base directory must be set");
        }
        return new FileSystemFileStorage(baseDirectory);
    }
}
