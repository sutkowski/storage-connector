package pl.sutkowski.storageconnector.autoconfigure.memory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.api.FileStorageImplementation;
import pl.sutkowski.api.FileStorageImplementor;
import pl.sutkowski.storageconnector.memory.InMemoryFileStorageImplementor;

@Configuration
@ConditionalOnClass(InMemoryFileStorageImplementor.class)
public class InMemoryFileStorageAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(FileStorageImplementor.class)
    public FileStorageImplementor fileStorageImplementor() {
        return new InMemoryFileStorageImplementor();
    }
    
    @Bean
    @ConditionalOnMissingBean(FileStorage.class)
    public FileStorage fileStorage(FileStorageImplementor fileStorageImplementor) {
        return new FileStorageImplementation(fileStorageImplementor);
    }
}
