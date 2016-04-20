package pl.sutkowski.storageconnector.googledrive;

import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sutkowski.api.FileHolder;
import pl.sutkowski.api.FileLocationHolder;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.api.exception.FileStorageException;
import pl.sutkowski.api.impl.PathFileLocationHolder;
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
    public FileHolder download(FileLocationHolder url) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            OutputStream out = new ByteArrayOutputStream();
            System.err.println(url.toString());

            drive.files().get(url.toString()).executeMediaAndDownloadTo(out);

            MediaHttpDownloader downloader =
                    new MediaHttpDownloader(googleDriveClient.getHttpTransport(), drive.getRequestFactory().getInitializer());
            downloader.download(new GenericUrl(""), out);
            return FileStorageUtils.toByteArray(outputStream);
        } catch (Exception ex) {
            throw new FileStorageException(ex);
        }
    }

    @Override
    public void remove(FileLocationHolder url) {
        try {
            drive.files().delete("fileId").execute();
        } catch (IOException ex) {
            throw new FileStorageException(ex);
        }
    }

    @Override
    public FileLocationHolder upload(FileHolder  content, FileLocationHolder url) {
        try {
            File file = new File();
            file.setTitle(url.toString());

            final java.io.File fileContent = FileStorageUtils.toTmpFile(content);
            FileContent mediaContent = new FileContent("plain/text", fileContent);

            final Drive.Files.Insert create = drive.files().insert(file,mediaContent);
            final File execute = create.execute();
            FileStorageUtils.removeTmpFile(fileContent);

            return new PathFileLocationHolder(Paths.get(execute.getId()));
        } catch (IOException ex) {
            throw FileStorageException.uploadFailed(ex);
        }
    }

}
