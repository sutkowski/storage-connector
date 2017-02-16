package pl.sutkowski.storageconnector.amazons3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.storageconnector.amazons3.impl.AmazonS3ConfigProvider;
import pl.sutkowski.storageconnector.amazons3.impl.PropertiesAmazonS3ConfigProvider;
import pl.sutkowski.storageconnector.test.base.contract.FileStorageContractTestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@SpringApplicationConfiguration(classes = {AmazonS3ClientTestBase.TestConfig.class})
public abstract class AmazonS3ClientTestBase extends FileStorageContractTestBase {

    @Configuration
    @PropertySource(value = "file:${user.home}/amazons3.yml", ignoreResourceNotFound = false)
    @ComponentScan(basePackages = "pl.sutkowski.storageconnector.amazons3")
    public static class TestConfig {

        @Bean
        AmazonS3ConfigProvider amazonS3ConfigProvider() {
            return new PropertiesAmazonS3ConfigProvider();
        }

        @Bean
        AWSCredentials awsCredentials(AmazonS3ConfigProvider amazonS3ConfigProvider) {
            return new BasicAWSCredentials(amazonS3ConfigProvider.getAccessKey(), amazonS3ConfigProvider.getSecretKey());
        }

        @Bean
        AWSCredentialsProvider awsCredentialsProvider(AWSCredentials awsCredentials) {
            return new StaticCredentialsProvider(awsCredentials);
        }

        @Bean
        AmazonS3Client amazonS3Client(AWSCredentialsProvider awsCredentialsProvider) {
            return new AmazonS3Client(awsCredentialsProvider);
        }

        @Bean
        PlaceholderConfigurerSupport placeholderConfigurerSupport() {
            return new PropertySourcesPlaceholderConfigurer();
        }

        @Bean
        public FileStorage fileStorage(AmazonS3Client amazonS3Client, AmazonS3ConfigProvider amazonS3ConfigProvider) {
            return new DefaultAmazonS3FileStorage(amazonS3Client, amazonS3ConfigProvider.getBucketName());
        }

    }
}
