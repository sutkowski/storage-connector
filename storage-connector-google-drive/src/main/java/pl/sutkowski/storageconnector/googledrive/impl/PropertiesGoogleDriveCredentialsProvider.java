package pl.sutkowski.storageconnector.googledrive.impl;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class PropertiesGoogleDriveCredentialsProvider implements GoogleDriveCredentialsProvider {

    @Value("${googledrive.app.clientId}")
    private String clientId;

    @Value("${googledrive.app.clientSecret}")
    private String clientSecret;

    @Value("${googledrive.app.accessToken}")
    private String accessToken;

}
