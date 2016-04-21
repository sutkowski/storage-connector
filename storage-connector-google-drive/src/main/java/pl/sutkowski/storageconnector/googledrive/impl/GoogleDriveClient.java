package pl.sutkowski.storageconnector.googledrive.impl;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import lombok.Getter;
import pl.sutkowski.api.exception.FileStorageInitializationException;

public class GoogleDriveClient {

    @Getter
    private HttpTransport httpTransport;
    private GoogleDriveCredentialsProvider googleDriveCredentialsProvider;

    private static final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

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
                jsonFactory,
                authorize())
                .setApplicationName("").build();
    }

    private Credential authorize() {
        try {
            return GoogleCredential.fromStream(new FileInputStream(googleDriveCredentialsProvider.getGoogleCredentialFileLocation()))
                    .createScoped(Collections.singleton(DriveScopes.DRIVE));
        } catch (IOException e) {
            throw new FileStorageInitializationException(e);
        }
    }

}
