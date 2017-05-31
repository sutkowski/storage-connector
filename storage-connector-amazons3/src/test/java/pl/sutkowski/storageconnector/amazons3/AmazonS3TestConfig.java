package pl.sutkowski.storageconnector.amazons3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import pl.sutkowski.api.FileStorageImplementor;
import pl.sutkowski.storageconnector.amazons3.impl.AmazonS3ConfigProvider;

@Configuration
@PropertySource(value = "file:${user.home}/amazons3.yml", ignoreResourceNotFound = false)
@ComponentScan(basePackages = "pl.sutkowski.storageconnector.amazons3")
public class AmazonS3TestConfig {

    @Bean
    AmazonS3ConfigProvider amazonS3ConfigProvider() {
        return new TestAmazonS3ConfigProvider();
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
    public FileStorageImplementor fileStorage(AmazonS3Client amazonS3Client, AmazonS3ConfigProvider amazonS3ConfigProvider) {
        return new AmazonS3FileStorageImplementor(amazonS3Client, amazonS3ConfigProvider.getBucketName());
    }

}
