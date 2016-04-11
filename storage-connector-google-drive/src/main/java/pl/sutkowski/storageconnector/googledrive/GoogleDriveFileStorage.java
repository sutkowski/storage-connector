package pl.sutkowski.storageconnector.googledrive;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.api.exception.FileStorageException;
import pl.sutkowski.storageconnector.googledrive.impl.GoogleDriveClient;
import pl.sutkowski.utils.FileStorageUtils;

public class GoogleDriveFileStorage implements FileStorage {

    final GoogleDriveClient googleDriveClient;
    private Drive drive;

    @Autowired
    public GoogleDriveFileStorage(GoogleDriveClient googleDriveClient) {
        this.googleDriveClient = googleDriveClient;
        this.drive = googleDriveClient.getDrive();
    }

    @Override
    public byte[] download(Path url) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            OutputStream out = new ByteArrayOutputStream();

            drive.files().get("fileId").executeMediaAndDownloadTo(out);

//            MediaHttpDownloader downloader =
//                    new MediaHttpDownloader(googleDriveClient.getHttpTransport(), drive.getRequestFactory().getInitializer());
//            downloader.download(new GenericUrl(""), out);
            return FileStorageUtils.toByteArray(outputStream);
        } catch (Exception ex) {
            throw new FileStorageException(ex);
        }
    }

    @Override
    public void remove(Path url) {
        try {
            drive.files().delete("fileId").execute();
        } catch (IOException ex) {
            throw new FileStorageException(ex);
        }
    }

    @Override
    public Path upload(byte[] content, Path url) {
        try {
            File file = new File();
            file.setName(url.toString());
            file.setId("fileId");

            final java.io.File fileContent = FileStorageUtils.toTmpFile(content);
            FileContent mediaContent = new FileContent("plain/text", fileContent);

            final Drive.Files.Create create = drive.files().create(file,mediaContent);
            FileStorageUtils.removeTmpFile(fileContent);
        } catch (IOException ex) {
            throw FileStorageException.uploadFailed(ex);
        }
        return null;
    }

}
