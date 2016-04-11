package pl.sutkowski.storageconnector.googledrive.impl;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.services.drive.Drive;
import lombok.Getter;
import pl.sutkowski.storageconnector.googledrive.util.GoogleDriveCommons;

public class GoogleDriveClient {

    @Getter
    private HttpTransport httpTransport;
    private GoogleDriveCredentialsProvider googleDriveCredentialsProvider;

    @Getter
    private Drive drive;

    public GoogleDriveClient(GoogleDriveCredentialsProvider googleDriveCredentialsProvider) throws Exception {
        this.googleDriveCredentialsProvider = googleDriveCredentialsProvider;
        prepareDrive();
    }

    private void prepareDrive() throws Exception {
        httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        drive = new Drive.Builder(
                httpTransport,
                GoogleDriveCommons.getJsonFactory(),
                authorize())
                .setApplicationName(GoogleDriveCommons.getApplicationName()).build();
    }

    private Credential authorize() throws Exception {
        return new GoogleCredential().setAccessToken(googleDriveCredentialsProvider.getAccessToken());
    }

}
