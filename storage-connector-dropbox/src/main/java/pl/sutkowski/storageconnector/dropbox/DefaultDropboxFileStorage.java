package pl.sutkowski.storageconnector.dropbox;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxWriteMode;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sutkowski.api.exception.FileStorageException;
import pl.sutkowski.storageconnector.dropbox.impl.DropboxClient;
import pl.sutkowski.utils.ConversionUtils;

public class DefaultDropboxFileStorage implements DropboxFileStorage {

    private DbxClient client;

    @Autowired
    public DefaultDropboxFileStorage(DropboxClient dropboxClient) {
        client = dropboxClient.getClient();
    }

    @Override
    public Path upload(byte[] content, Path url) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(content)) {
            client.uploadFile(getFullPath(url), DbxWriteMode.add(), content.length, inputStream);
            return url;
        } catch (Exception ex) {
            throw FileStorageException.uploadFailed(ex);
        }
    }

    @Override
    public byte[] download(Path url) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            DbxEntry.File file = client.getFile(getFullPath(url), null, outputStream);
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
        try {
            client.delete(getFullPath(url));
        } catch (Exception e) {
            throw FileStorageException.fileNotFound(url);
        }
    }

    @Override
    public Path createFolder(Path url) {
        try {
            if (client.createFolder(getLocationPath(url)) == null) {
                throw FileStorageException.createFolder(getLocationPath(url));
            }
        } catch (DbxException e) {
            throw FileStorageException.createFolder(getLocationPath(url));
        }
        return url;
    }

    @Override
    public Path move(Path fromPath, Path toPath) {
        try {
            if (client.move(getFullPath(fromPath), getFullPath(toPath)) == null) {
                throw FileStorageException.moveException(getFullPath(fromPath), getFullPath(toPath));
            }
        } catch (DbxException e) {
            throw FileStorageException.moveException(getFullPath(fromPath), getFullPath(toPath));
        }
        return toPath;
    }

    private String getFileName(Path url) {
        return url.getFileName().toString();
    }

    private String getLocationPath(Path url) {
        return url.getParent().toString().replaceAll("\\\\", "/");
    }

    private String getFullPath(Path url) {
        return getLocationPath(url) + "/" + getFileName(url);
    }
}
