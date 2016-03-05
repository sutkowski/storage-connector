package pl.sutkowski.storageconnector.dropbox.impl;

import com.dropbox.core.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sutkowski.storageconnector.dropbox.DropboxCredentialsProvider;
import pl.sutkowski.storageconnector.test.utils.ContentProvider;
import pl.sutkowski.storageconnector.test.utils.TmpDataContentProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

@Service
@Slf4j
public class DropboxClient {

    @Autowired
    DropboxCredentialsProvider dropboxCredentialsProvider;

    ContentProvider contentProvider = new TmpDataContentProvider();

    public void flow() throws IOException, DbxException {
        DbxClient client = authenticate();
        uploadFile(client);
        listItems(client);
        downloadFile(client);
    }

    private DbxClient authenticate() throws DbxException {
        final String appKey = dropboxCredentialsProvider.getAppKey();
        final String appSecret = dropboxCredentialsProvider.getAppSecret();
        final String code = dropboxCredentialsProvider.getAccessToken();

        DbxAppInfo appInfo = new DbxAppInfo(appKey, appSecret);

        DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0",
                Locale.getDefault().toString());
        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);

        String authorizeUrl = webAuth.start();
        DbxAuthFinish authFinish = webAuth.finish(code);
        String accessToken = authFinish.accessToken;

        DbxClient client = new DbxClient(config, accessToken);

        System.out.println("Linked account: " + client.getAccountInfo().displayName);
        return client;
    }

    private void downloadFile(DbxClient client) throws DbxException, IOException {
        FileOutputStream outputStream = new FileOutputStream("magnum-opus.txt");
        try {
            DbxEntry.File downloadedFile = client.getFile("/magnum-opus.txt", null,
                    outputStream);
            System.out.println("Metadata: " + downloadedFile.toString());
        } finally {
            outputStream.close();
        }
    }

    private void listItems(DbxClient client) throws DbxException {
        DbxEntry.WithChildren listing = client.getMetadataWithChildren("/");
        System.out.println("Files in the root path:");
        for (DbxEntry child : listing.children) {
            System.out.println("	" + child.name + ": " + child.toString());
        }
    }

    private void uploadFile(DbxClient client) throws DbxException, IOException {
        File inputFile = new File("/home/dropbox.yml");
        FileInputStream inputStream = new FileInputStream(inputFile);
        try {
            DbxEntry.File uploadedFile = client.uploadFile("/magnum-opus.txt",
                    DbxWriteMode.add(), inputFile.length(), inputStream);
            System.out.println("Uploaded: " + uploadedFile.toString());
        } finally {
            inputStream.close();
        }
    }
}