package pl.sutkowski.storageconnector.amazons3.impl;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class PropertiesAmazonS3ConfigProvider implements AmazonS3ConfigProvider {

    @Value("${amazons3.app.accessKey}")
    String accessKey;

    @Value("${amazons3.app.secretKey}")
    String secretKey;

    @Value("${amazons3.app.bucketName}")
    String bucketName;
}
