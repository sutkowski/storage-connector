package pl.sutkowski.storageconnector.test.base.contract;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import pl.sutkowski.api.exception.FileStorageException;
import pl.sutkowski.storageconnector.test.base.AbstractTestBase;

import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class FileStorageContractTestBase extends AbstractTestBase {

    Path url;

    @After
    public void cleanUp() throws Exception {
        try {
            getFileStorage().remove(url);
        } catch (FileStorageException ex) {
        }
    }

    @Test
    public void shouldUploadFileAndDownloadIt() throws Exception {

        url = getFileStorage().upload(getContent());
        byte[] download = getFileStorage().download(url);

        Assertions.assertThat(download).isEqualTo(getContent());
    }

    @Test
    public void shouldReturnFileStorageExceptionWhenDownloadingNonExistingFile() throws Exception {

        url = Paths.get("");

        thrown.expect(FileStorageException.class);
        getFileStorage().download(url);
    }

    @Test
    public void afterDeleteDownloadShouldReturnFileStorageException() throws Exception {

        url = getFileStorage().upload(getContent());
        getFileStorage().remove(url);

        thrown.expect(FileStorageException.class);
        getFileStorage().download(url);
    }

    @Test
    public void shouldReturnErrorWhenAttemptingToRemoveNonExistingFile() throws Exception {
        url = Paths.get("");
        thrown.expect(FileStorageException.class);
        getFileStorage().remove(url);
    }

    private byte[] getContent() {
        return getContentProvider().getContent();
    }

}
