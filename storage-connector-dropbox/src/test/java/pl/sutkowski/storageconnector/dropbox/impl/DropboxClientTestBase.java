package pl.sutkowski.storageconnector.dropbox.impl;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.storageconnector.dropbox.DropboxCredentialsProvider;
import pl.sutkowski.storageconnector.dropbox.DropboxFileStorage;
import pl.sutkowski.storageconnector.test.base.contract.TmpDataFileStorageContractTestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@SpringApplicationConfiguration(classes = {DropboxClientTestBase.TestConfig.class})
public abstract class DropboxClientTestBase extends TmpDataFileStorageContractTestBase {

    @Configuration
    @PropertySource(value = "file:/home/dropbox.yml", ignoreResourceNotFound = false)
    @ComponentScan(basePackages = "pl.sutkowski.storageconnector.dropbox")
    public static class TestConfig {

        @Bean
        PlaceholderConfigurerSupport placeholderConfigurerSupport(){
            return new PropertySourcesPlaceholderConfigurer();
        }
    }
}
