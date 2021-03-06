package pl.sutkowski.storageconnector.dropbox.impl;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxRequestConfig;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;

@Slf4j
public class DropboxClient {

    @Getter
    private DbxClient client;

    public DropboxClient(DropboxCredentialsProvider dropboxCredentialsProvider) {
        DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString());
        client = new DbxClient(config, dropboxCredentialsProvider.getAccessToken());
    }

}