package pl.sutkowski.storageconnector.dropbox;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxWriteMode;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sutkowski.api.FileHolder;
import pl.sutkowski.api.FileLocationHolder;
import pl.sutkowski.api.exception.FileStorageException;
import pl.sutkowski.api.impl.ByteFileHolder;
import pl.sutkowski.api.utils.FileStorageUtils;
import pl.sutkowski.storageconnector.dropbox.impl.DropboxClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class DefaultDropboxFileStorage implements DropboxFileStorage {

    private DbxClient client;

    @Autowired
    public DefaultDropboxFileStorage(DropboxClient dropboxClient) {
        client = dropboxClient.getClient();
    }

    @Override
    public FileLocationHolder upload(FileHolder  content, FileLocationHolder url) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes())) {
            client.uploadFile(FileStorageUtils.getFullPath(url), DbxWriteMode.add(), content.getBytes().length, inputStream);
            return url;
        } catch (Exception ex) {
            throw FileStorageException.uploadFailed(ex);
        }
    }

    @Override
    public FileHolder download(FileLocationHolder url) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            DbxEntry.File file = client.getFile(FileStorageUtils.getFullPath(url), null, outputStream);
            if (!file.isFile()) {
                throw FileStorageException.fileNotFound(url.getPath());
            }
            return new ByteFileHolder(FileStorageUtils.toByteArray(outputStream));
        } catch (Exception ex) {
            throw FileStorageException.downloadFailed(ex);
        }
    }

    @Override
    public void remove(FileLocationHolder url) {
        try {
            client.delete(FileStorageUtils.getFullPath(url));
        } catch (Exception e) {
            throw FileStorageException.fileNotFound(url.getPath());
        }
    }

    @Override
    public FileLocationHolder createFolder(FileLocationHolder url) {
        try {
            if (client.createFolder(FileStorageUtils.getLocationPath(url)) == null) {
                throw FileStorageException.createFolder(FileStorageUtils.getLocationPath(url));
            }
        } catch (DbxException e) {
            throw FileStorageException.createFolder(FileStorageUtils.getLocationPath(url));
        }
        return url;
    }

    @Override
    public FileLocationHolder move(FileLocationHolder fromPath, FileLocationHolder toPath) {
        try {
            if (client.move(FileStorageUtils.getFullPath(fromPath), FileStorageUtils.getFullPath(toPath)) == null) {
                throw FileStorageException.moveException(FileStorageUtils.getFullPath(fromPath), FileStorageUtils.getFullPath(toPath));
            }
        } catch (DbxException e) {
            throw FileStorageException.moveException(FileStorageUtils.getFullPath(fromPath), FileStorageUtils.getFullPath(toPath));
        }
        return toPath;
    }

}
