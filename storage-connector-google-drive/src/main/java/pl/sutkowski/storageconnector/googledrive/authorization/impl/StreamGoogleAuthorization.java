package pl.sutkowski.storageconnector.googledrive.authorization.impl;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.drive.DriveScopes;
import pl.sutkowski.api.exception.FileStorageInitializationException;
import pl.sutkowski.storageconnector.googledrive.authorization.GoogleAuthorization;
import pl.sutkowski.storageconnector.googledrive.authorization.GoogleDriveCredentialsProvider;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;

public class StreamGoogleAuthorization implements GoogleAuthorization {

    private GoogleDriveCredentialsProvider googleDriveCredentialsProvider;

    public StreamGoogleAuthorization(GoogleDriveCredentialsProvider googleDriveCredentialsProvider) {
        this.googleDriveCredentialsProvider = googleDriveCredentialsProvider;
    }

    @Override
    public Credential authorize() {
            try {
                return GoogleCredential.fromStream(new FileInputStream(googleDriveCredentialsProvider.getGoogleCredentialFileLocation()))
                        .createScoped(Collections.singleton(DriveScopes.DRIVE));
            } catch (IOException e) {
                throw new FileStorageInitializationException(e);
            }
        }
}
