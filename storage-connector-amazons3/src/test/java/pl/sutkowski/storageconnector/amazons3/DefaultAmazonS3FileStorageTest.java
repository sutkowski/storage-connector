package pl.sutkowski.storageconnector.amazons3;

import org.springframework.beans.factory.annotation.Autowired;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.api.FileStorageProvider;


public class DefaultAmazonS3FileStorageTest extends AmazonS3ClientTestBase {

    @Autowired
    FileStorage fileStorage;

    @Override
    public FileStorageProvider getFileStorageProvider() {
        return () -> fileStorage;
    }

}