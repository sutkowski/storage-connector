package pl.sutkowski.storageconnector.autoconfigure.amazons3;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import pl.sutkowski.storageconnector.amazons3.impl.AmazonS3ConfigProvider;

@Getter
public class PropertiesAmazonS3ConfigProvider implements AmazonS3ConfigProvider {

    @Value("${amazons3.app.accessKey}")
    String accessKey;

    @Value("${amazons3.app.secretKey}")
    String secretKey;

    @Value("${amazons3.app.bucketName}")
    String bucketName;
}
