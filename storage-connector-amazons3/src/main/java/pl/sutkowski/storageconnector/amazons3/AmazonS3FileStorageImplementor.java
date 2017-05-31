package pl.sutkowski.storageconnector.amazons3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import java.io.ByteArrayInputStream;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import pl.sutkowski.api.FileHolder;
import pl.sutkowski.api.FileLocationHolder;
import pl.sutkowski.api.FileStorageImplementor;
import pl.sutkowski.api.exception.FileStorageException;
import pl.sutkowski.api.impl.ByteFileHolder;
import pl.sutkowski.api.utils.FileStorageUtils;

@Slf4j
public class AmazonS3FileStorageImplementor implements FileStorageImplementor {

    private final AmazonS3Client client;
    private final String bucketName;

    public AmazonS3FileStorageImplementor(AmazonS3Client client, String bucketName) {
        this.client = client;
        this.bucketName = bucketName;
    }

    @Override
    public FileHolder download(FileLocationHolder url) {
        try {
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, url.getPathAsString());
            S3Object object = client.getObject(getObjectRequest);
            return new ByteFileHolder(FileStorageUtils.toByteArray(object.getObjectContent()));
        } catch (Exception ex) {
            throw FileStorageException.downloadFailed(ex);
        }
    }

    @Override
    public void remove(FileLocationHolder url) {
        if(Objects.isNull(url)) {
            throw FileStorageException.pathNotFound();
        }
        if (StringUtils.isEmpty(url.getPathAsString()) || !client.doesObjectExist(bucketName, url.getPathAsString())) {
            throw FileStorageException.fileNotFound(url.getPath());
        }
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, url.getPathAsString());
        client.deleteObject(deleteObjectRequest);
    }

    @Override
    public FileLocationHolder upload(FileHolder content, FileLocationHolder url) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes())) {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, url.getPathAsString(), inputStream, new ObjectMetadata());
            client.putObject(putObjectRequest);
            return url;
        } catch (Exception ex) {
            throw FileStorageException.uploadFailed(ex);
        }
    }

}
