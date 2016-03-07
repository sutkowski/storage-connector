package pl.sutkowski.storageconnector.dropbox;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxWriteMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.api.exception.FileStorageException;
import pl.sutkowski.storageconnector.dropbox.impl.DropboxClient;
import pl.sutkowski.utils.ConversionUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class DropboxFileStorage implements FileStorage {

    @Autowired
    DropboxClient dropboxClient;

    @Override
    public Path upload(byte[] content) {
        final String newId = UUID.randomUUID().toString();
        final Path url = Paths.get(newId);
        DbxClient client = dropboxClient.getClient();

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(content)) {
            client.uploadFile("/" + url.getFileName().toString(), DbxWriteMode.add(), content.length, inputStream);
            return url;
        } catch (Exception ex) {
            throw FileStorageException.uploadFailed(ex);
        }
    }


    @Override
    public byte[] download(Path url) {
        DbxClient client = dropboxClient.getClient();
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            DbxEntry.File file = client.getFile("/" + url.getFileName().toString(), null, outputStream);
            if (!file.isFile()) {
                throw new FileStorageException("File not found");
            }
            return ConversionUtils.toByteArray(outputStream);
        } catch (Exception ex) {
            throw new FileStorageException(ex);
        }
    }

    @Override
    public void remove(Path url) {
        DbxClient client = dropboxClient.getClient();
        try {
            client.delete("/" + url.getFileName().toString());
        } catch (Exception e) {
            throw FileStorageException.fileNotFound(url);
        }
    }
}
