package pl.sutkowski.storageconnector.autoconfigure.amazons3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;
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
import pl.sutkowski.storageconnector.amazons3.AmazonS3FileStorageImplementor;
import pl.sutkowski.storageconnector.amazons3.impl.AmazonS3ConfigProvider;

@Configuration
@ConditionalOnClass(AmazonS3FileStorageImplementor.class)
@PropertySource(value = "file:${user.home}/amazons3.yml", ignoreResourceNotFound = true)
public class AmazonS3FileStorageAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(FileStorageImplementor.class)
    public FileStorageImplementor fileStorageImplementor(AmazonS3Client amazonS3Client, AmazonS3ConfigProvider amazonS3ConfigProvider) {
        return new AmazonS3FileStorageImplementor(amazonS3Client, amazonS3ConfigProvider.getBucketName());
    }

    @Bean
    @ConditionalOnMissingBean(FileStorage.class)
    public FileStorage fileStorage(FileStorageImplementor fileStorageImplementor) {
        return new FileStorageImplementation(fileStorageImplementor);
    }

    @Bean
    @ConditionalOnMissingBean(AmazonS3ConfigProvider.class)
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
    AmazonS3Client amazonS3Client() {
        return new AmazonS3Client();
    }

    @Bean
    @ConditionalOnMissingBean(PlaceholderConfigurerSupport.class)
    PlaceholderConfigurerSupport placeholderConfigurerSupport() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
