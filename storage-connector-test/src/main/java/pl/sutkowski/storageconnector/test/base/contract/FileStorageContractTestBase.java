package pl.sutkowski.storageconnector.test.base.contract;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import pl.sutkowski.api.FileStorage;
import pl.sutkowski.api.exception.FileStorageException;
import pl.sutkowski.storageconnector.test.base.AbstractTestBase;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author eS
 */
public abstract class FileStorageContractTestBase extends AbstractTestBase {


    @Test
    public void shouldUploadFileAndDownloadIt() throws Exception {

        Path url = getFileStorage().upload(getContent());
        byte[] download = getFileStorage().download(url);

        Assertions.assertThat(download).isEqualTo(getContent());
    }

    @Test
    public void shouldReturnFileStorageExceptionWhenDownloadingNonExistingFile() throws Exception {

        Path url = Paths.get("");

        thrown.expect(FileStorageException.class);
        getFileStorage().download(url);
    }

    @Test
    public void afterDeleteDownloadShouldReturnFileStorageException() throws Exception {

        Path url = getFileStorage().upload(getContent());
        getFileStorage().remove(url);

        thrown.expect(FileStorageException.class);
        getFileStorage().download(url);
    }

    private FileStorage getFileStorage() throws Exception {
        return getFileStorageProvider().getFileStorage();
    }

    private byte[] getContent() {
        return getContentProvider().getContent();
    }

}
