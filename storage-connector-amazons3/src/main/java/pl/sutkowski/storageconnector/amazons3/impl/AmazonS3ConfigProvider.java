package pl.sutkowski.storageconnector.amazons3.impl;

public interface AmazonS3ConfigProvider {

    String getAccessKey();

    String getSecretKey();

    String getBucketName();
}
