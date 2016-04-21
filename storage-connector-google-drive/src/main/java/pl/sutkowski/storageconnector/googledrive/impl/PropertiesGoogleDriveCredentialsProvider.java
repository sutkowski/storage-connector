package pl.sutkowski.storageconnector.googledrive.impl;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class PropertiesGoogleDriveCredentialsProvider implements GoogleDriveCredentialsProvider {

    @Value("${googledrive.app.credentials.location}")
    private String googleCredentialFileLocation;

}
