package pl.sutkowski.storageconnector.test.base.contract;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import pl.sutkowski.api.FileHolder;
import pl.sutkowski.api.FileLocationHolder;
import pl.sutkowski.api.exception.FileStorageException;
import pl.sutkowski.storageconnector.test.base.AbstractTestBase;

import java.nio.file.Paths;
import pl.sutkowski.storageconnector.test.utils.TmpDataContentProvider;

public abstract class FileStorageContractTestBase extends AbstractTestBase {

    FileLocationHolder url;

    @After
    public void cleanUp() throws Exception {
        try {
            getFileStorage().remove(url);
        } catch (FileStorageException ex) {
            //This was intentionally left blank
        }
    }

    @Test
    public void shouldUploadFileAndDownloadIt() throws Exception {

        url = getFileStorage().upload(getTmpDataContent());
        FileHolder  download = getFileStorage().download(url);

        Assertions.assertThat(download).isEqualTo(getTmpDataContent());
    }

    @Test
    public void shouldUploadFileInFolderAndDownloadIt() throws Exception {
        FileLocationHolder path = getFileStorage().produceFileLocationHolder(Paths.get("/path/file"));

        url = getFileStorage().upload(getTmpDataContent(), path);
        FileHolder  download = getFileStorage().download(url);

        Assertions.assertThat(download).isEqualTo(getTmpDataContent());
    }

    @Test
    public void shouldReturnFileStorageExceptionWhenDownloadingNonExistingFile() throws Exception {

        url = getFileStorage().produceFileLocationHolder(Paths.get(""));

        thrown.expect(FileStorageException.class);
        getFileStorage().download(url);
    }

    @Test
    public void afterDeleteDownloadShouldReturnFileStorageException() throws Exception {

        url = getFileStorage().upload(getTmpDataContent());
        getFileStorage().remove(url);

        thrown.expect(FileStorageException.class);
        getFileStorage().download(url);
    }

    @Test
    public void shouldReturnErrorWhenAttemptingToRemoveFileWithEmptyNameAndPath() throws Exception {
        url = getFileStorage().produceFileLocationHolder(Paths.get(""));
        thrown.expect(FileStorageException.class);
        getFileStorage().remove(url);
    }

    @Test
    public void shouldReturnErrorWhenAttemptingToRemoveNonExistingFile() throws Exception {
        url = getFileStorage().produceFileLocationHolder(Paths.get("non-existing-file"));
        thrown.expect(FileStorageException.class);
        getFileStorage().remove(url);
    }


    @Test
    public void shouldMoveUploadedFile() throws Exception {
        FileLocationHolder path1 = getFileStorage().produceFileLocationHolder(Paths.get("/path1/file"));
        FileLocationHolder path2 = getFileStorage().produceFileLocationHolder(Paths.get("/path2/file"));
        url = getFileStorage().upload(getTmpDataContent(),path1);
        url = getFileStorage().move(url, path2);
        FileHolder  download = getFileStorage().download(url);

        Assertions.assertThat(download).isEqualTo(getTmpDataContent());
    }

    private FileHolder getTmpDataContent() {
        return new TmpDataContentProvider().getContent();
    }

}
