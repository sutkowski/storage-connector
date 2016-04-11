package pl.sutkowski.storageconnector.googledrive.impl;

public interface GoogleDriveCredentialsProvider {

    String getClientId();

    String getClientSecret();

    String getAccessToken();
}
