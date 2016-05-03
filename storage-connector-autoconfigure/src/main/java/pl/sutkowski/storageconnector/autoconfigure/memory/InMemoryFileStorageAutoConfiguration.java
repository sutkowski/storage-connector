package pl.sutkowski.storageconnector.autoconfigure.memory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.storageconnector.memory.InMemoryFileStorage;

@Configuration
@ConditionalOnClass(InMemoryFileStorage.class)
public class InMemoryFileStorageAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(FileStorage.class)
    public FileStorage fileStorage() {
        return new InMemoryFileStorage();
    }
}
