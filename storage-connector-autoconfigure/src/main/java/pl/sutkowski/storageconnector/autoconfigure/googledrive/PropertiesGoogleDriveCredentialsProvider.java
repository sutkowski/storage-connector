package pl.sutkowski.storageconnector.autoconfigure.googledrive;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import pl.sutkowski.storageconnector.googledrive.authorization.GoogleDriveCredentialsProvider;

@Getter
public class PropertiesGoogleDriveCredentialsProvider implements GoogleDriveCredentialsProvider {

    @Value("${googledrive.app.credentials.location}")
    private String googleCredentialFileLocation;

}
