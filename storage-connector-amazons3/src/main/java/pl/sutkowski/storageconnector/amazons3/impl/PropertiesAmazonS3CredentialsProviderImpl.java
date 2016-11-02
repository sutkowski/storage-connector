package pl.sutkowski.storageconnector.amazons3.impl;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class PropertiesAmazonS3CredentialsProviderImpl implements AmazonS3CredentialsProvider {

    @Value("${amazons3.app.accessKey}")
    String accessKey;

    @Value("${amazons3.app.secretKey}")
    String secretKey;
}
