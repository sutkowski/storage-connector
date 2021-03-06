package pl.sutkowski.storageconnector.googledrive;

import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Objects;
import pl.sutkowski.api.FileHolder;
import pl.sutkowski.api.FileLocationHolder;
import pl.sutkowski.api.FileStorageImplementor;
import pl.sutkowski.api.exception.FileStorageException;
import pl.sutkowski.api.impl.ByteFileHolder;
import pl.sutkowski.api.utils.FileStorageUtils;
import pl.sutkowski.storageconnector.googledrive.impl.GoogleDriveClient;
import pl.sutkowski.storageconnector.googledrive.impl.holder.GoogleDriveFileLocationHolder;

public class GoogleDriveFileStorageImplementor implements FileStorageImplementor {

    final GoogleDriveClient googleDriveClient;
    private Drive drive;

    public GoogleDriveFileStorageImplementor(GoogleDriveClient googleDriveClient) {
        this.googleDriveClient = googleDriveClient;
        this.drive = googleDriveClient.getDrive();
    }

    @Override
    public FileHolder download(FileLocationHolder genericUrl) {
        assertGoogleDriveFileLocationHolder(genericUrl);
        GoogleDriveFileLocationHolder url = (GoogleDriveFileLocationHolder) genericUrl;
        try (InputStream inputStream = downloadFile(drive, url.getFile())) {
            return new ByteFileHolder(FileStorageUtils.toByteArray(inputStream));
        } catch (Exception ex) {
            throw FileStorageException.downloadFailed(ex);
        }
    }

    @Override
    public void remove(FileLocationHolder genericUrl) {
        assertGoogleDriveFileLocationHolder(genericUrl);
        GoogleDriveFileLocationHolder url = (GoogleDriveFileLocationHolder) genericUrl;
        try {
            drive.files().delete(url.getFile().getId()).execute();
        } catch (IOException ex) {
            throw FileStorageException.removeFailed(ex);
        }
    }

    @Override
    public GoogleDriveFileLocationHolder upload(FileHolder content, FileLocationHolder url) {
        assertGoogleDriveFileLocationHolder(url);
        try {
            File file = new File();
            file.setTitle(url.toString());

            final java.io.File fileContent = FileStorageUtils.toTmpFile(content.getBytes());
            FileContent mediaContent = new FileContent("plain/text", fileContent);

            final Drive.Files.Insert create = drive.files().insert(file, mediaContent);
            final File execute = create.execute();
            FileStorageUtils.removeTmpFile(fileContent);

            return new GoogleDriveFileLocationHolder(execute);
        } catch (IOException ex) {
            throw FileStorageException.uploadFailed(ex);
        }
    }

    @Override
    public FileLocationHolder produceFileLocationHolder(Path path) {
        File file = new File();
        file.setId(path.getFileName().toString());
        return new GoogleDriveFileLocationHolder(file);
    }

    private InputStream downloadFile(Drive service, File file) {
        if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
            try {
                HttpResponse resp =
                        service.getRequestFactory()
                                .buildGetRequest(new GenericUrl(file.getDownloadUrl()))
                                .execute();
                return resp.getContent();
            } catch (Exception ex) {
                throw FileStorageException.downloadFailed(ex);
            }
        }
        throw FileStorageException.downloadFailed();
    }

    private void assertGoogleDriveFileLocationHolder(FileLocationHolder genericUrl) {
        if(Objects.isNull(genericUrl)) {
            throw FileStorageException.pathNotFound();
        }
        if (!(genericUrl instanceof GoogleDriveFileLocationHolder)) {
            throw new IllegalArgumentException("Not Supported Location Holder instance");
        }
    }
}
