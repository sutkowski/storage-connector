package pl.sutkowski.storageconnector.googledrive.authorization;

import com.google.api.client.auth.oauth2.Credential;

public interface GoogleAuthorization {

    Credential authorize();
}
