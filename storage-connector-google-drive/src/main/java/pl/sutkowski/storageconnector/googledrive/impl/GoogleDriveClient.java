package pl.sutkowski.storageconnector.googledrive.impl;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import lombok.Getter;
import pl.sutkowski.api.exception.FileStorageInitializationException;
import pl.sutkowski.storageconnector.googledrive.authorization.GoogleAuthorization;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class GoogleDriveClient {

    private static final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

    @Getter
    private HttpTransport httpTransport;

    private GoogleAuthorization googleAuthorization;

    @Getter
    private Drive drive;

    public GoogleDriveClient(GoogleAuthorization googleAuthorization) {
        this.googleAuthorization = googleAuthorization;
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
                googleAuthorization.authorize())
                .setApplicationName("").build();
    }


}
