package pl.sutkowski.storageconnector.googledrive.impl;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
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
        try {
            return GoogleCredential.fromStream(new FileInputStream("/home/es/My Project-ef1f016e4c64.json"))
                    .createScoped(Collections.singleton(DriveScopes.DRIVE));
        } catch (IOException e) {
            throw new FileStorageInitializationException(e);
        }
//                .setClientSecrets(
//                        googleDriveCredentialsProvider.getClientId(),
//                        googleDriveCredentialsProvider.getClientSecret())
//                .setServiceAccountScopes(Collections.singleton(
//                        DriveScopes.DRIVE
//                ))
//                .setServiceAccountId("storage-connector@appspot.gserviceaccount.com")
//                .setServiceAccountUser("storage.connector.test@gmail.com")
//                .setServiceAccountPrivateKeyId("ef1f016e4c64ca9bd71724f316a36731c5826777")
//                .build();
    }

}
