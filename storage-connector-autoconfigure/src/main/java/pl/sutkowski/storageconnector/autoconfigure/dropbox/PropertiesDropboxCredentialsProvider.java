package pl.sutkowski.storageconnector.autoconfigure.dropbox;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import pl.sutkowski.storageconnector.dropbox.impl.DropboxCredentialsProvider;

@Getter
public class PropertiesDropboxCredentialsProvider implements DropboxCredentialsProvider {

    @Value("${dropbox.app.key}")
    private String appKey;

    @Value("${dropbox.app.secret}")
    private String appSecret;

    @Value("${dropbox.app.accesstoken}")
    private String accessToken;
}
