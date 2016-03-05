package pl.sutkowski.storageconnector.dropbox.impl;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sutkowski.storageconnector.dropbox.DropboxCredentialsProvider;

import static org.assertj.core.api.Assertions.assertThat;

public class DropboxClientTest extends DropboxClientTestBase {

    @Autowired
    DropboxClient dropboxClient;

    @Autowired
    DropboxCredentialsProvider dropboxCredentialsProvider;

    @Test
    public void testPropertiesLoad() throws Exception {
        assertThat(dropboxCredentialsProvider.getAppKey()).doesNotContain("dropbox.app.key");
        assertThat(dropboxCredentialsProvider.getAppSecret()).doesNotContain("dropbox.app.secret");
        assertThat(dropboxCredentialsProvider.getAccessToken()).doesNotContain("dropbox.app.accesstoken");
    }

    @Test
    public void testMain() throws Exception {
        dropboxClient.flow();
    }

}