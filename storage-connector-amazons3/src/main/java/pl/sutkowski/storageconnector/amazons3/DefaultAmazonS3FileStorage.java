package pl.sutkowski.storageconnector.amazons3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sutkowski.api.FileHolder;
import pl.sutkowski.api.FileLocationHolder;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.api.exception.FileStorageException;
import pl.sutkowski.api.impl.ByteFileHolder;
import pl.sutkowski.api.utils.FileStorageUtils;

import java.io.ByteArrayInputStream;

import static pl.sutkowski.api.utils.FileStorageUtils.getFileName;
import static pl.sutkowski.api.utils.FileStorageUtils.getLocationPath;

@Slf4j
public class DefaultAmazonS3FileStorage implements FileStorage {

    private final AmazonS3Client client;

    @Autowired
    public DefaultAmazonS3FileStorage(AmazonS3Client client) {
        this.client = client;
    }

    @Override
    public FileHolder download(FileLocationHolder url) {
        try {
            GetObjectRequest getObjectRequest = new GetObjectRequest(getLocationPath(url), getFileName(url));
            S3Object object = client.getObject(getObjectRequest);
            return new ByteFileHolder(FileStorageUtils.toByteArray(object.getObjectContent()));
        } catch (Exception ex) {
            throw FileStorageException.downloadFailed(ex);
        }
    }

    @Override
    public void remove(FileLocationHolder url) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(getLocationPath(url), getFileName(url));
        client.deleteObject(deleteObjectRequest);
    }

    @Override
    public FileLocationHolder upload(FileHolder content, FileLocationHolder url) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes())) {
            PutObjectRequest putObjectRequest = new PutObjectRequest(getLocationPath(url), getFileName(url), inputStream, new ObjectMetadata());
            client.putObject(putObjectRequest);
            return url;
        } catch (Exception ex) {
            throw FileStorageException.uploadFailed(ex);
        }
    }
}
