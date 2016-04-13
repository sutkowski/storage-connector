package pl.sutkowski.storageconnector.googledrive.impl;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.services.drive.Drive;
import java.io.IOException;
import java.security.GeneralSecurityException;
import lombok.Getter;
import pl.sutkowski.api.exception.FileStorageInitializationException;
import pl.sutkowski.storageconnector.googledrive.util.GoogleDriveCommons;

public class GoogleDriveClient {

    @Getter
    private HttpTransport httpTransport;
    private GoogleDriveCredentialsProvider googleDriveCredentialsProvider;

    @Getter
    private Drive drive;

    public GoogleDriveClient(GoogleDriveCredentialsProvider googleDriveCredentialsProvider) {
        this.googleDriveCredentialsProvider = googleDriveCredentialsProvider;
        prepareDrive();
    }

    private void prepareDrive() {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException | IOException e) {
            throw new FileStorageInitializationException(e);
        }

        drive = new Drive.Builder(
                httpTransport,
                GoogleDriveCommons.getJsonFactory(),
                authorize())
                .setApplicationName(GoogleDriveCommons.getApplicationName()).build();
    }

    private Credential authorize() {
        return new GoogleCredential.Builder()
                .setClientSecrets(
                        googleDriveCredentialsProvider.getClientId(),
                        googleDriveCredentialsProvider.getClientSecret())
//                .setServiceAccountScopes(Collections.singleton("https://www.googleapis.com/auth/drive"))
//                .setServiceAccountId("storage-connector@appspot.gserviceaccount.com")
//                .setServiceAccountUser("storage.connector.test@gmail.com")
                .setServiceAccountPrivateKeyId("183e769a5896cd6e0d7866c1f847dc74e72b1341")
                .build();
    }

}
