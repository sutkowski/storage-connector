package pl.sutkowski.storageconnector.dropbox;

public interface DropboxCredentialsProvider {

    String getAppKey();

    String getAppSecret();

    String getAccessToken();
}
