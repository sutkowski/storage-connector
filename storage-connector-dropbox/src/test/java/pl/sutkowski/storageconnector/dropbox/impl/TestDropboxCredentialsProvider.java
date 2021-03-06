package pl.sutkowski.storageconnector.dropbox.impl;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class TestDropboxCredentialsProvider implements DropboxCredentialsProvider {

    @Value("${dropbox.app.key}")
    private String appKey;

    @Value("${dropbox.app.secret}")
    private String appSecret;

    @Value("${dropbox.app.accesstoken}")
    private String accessToken;
}
