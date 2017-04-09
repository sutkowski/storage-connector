package pl.sutkowski.storageconnector.googledrive.impl;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import pl.sutkowski.storageconnector.googledrive.authorization.GoogleDriveCredentialsProvider;

@Getter
public class TestGoogleDriveCredentialsProvider implements GoogleDriveCredentialsProvider {

    @Value("${googledrive.app.credentials.location}")
    private String googleCredentialFileLocation;

}
