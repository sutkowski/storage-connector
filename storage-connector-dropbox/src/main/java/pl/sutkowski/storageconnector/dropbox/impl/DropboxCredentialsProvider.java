package pl.sutkowski.storageconnector.dropbox.impl;

public interface DropboxCredentialsProvider {

    String getAppKey();

    String getAppSecret();

    String getAccessToken();
}
