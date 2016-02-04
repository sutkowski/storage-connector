package pl.sutkowski.storageconnector.test.base;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.storageconnector.test.ContentProvider;

import java.nio.file.Path;

/**
 * @author eS
 */
public abstract class FileStorageTestBase {

    @Test
    public void shouldUploadFileAndDownloadIt() throws Exception {
        FileStorage fileStorage = getFileStorage();
        byte[] upload = getContent();

        Path url = fileStorage.upload(upload);
        byte[] download = fileStorage.download(url);

        Assertions.assertThat(download).isEqualTo(upload);
    }

    private FileStorage getFileStorage() throws Exception {
        return getFileStorageProvider().getFileStorage();
    }

    private byte[] getContent() {
        return getContentProvider().getContent();
    }

    public abstract ContentProvider getContentProvider();

    public abstract pl.sutkowski.api.FileStorageProvider getFileStorageProvider();
}
