package pl.sutkowski.storageconnector.amazons3.impl;

public interface AmazonS3CredentialsProvider {

    String getAccessKey();

    String getSecretKey();
}
