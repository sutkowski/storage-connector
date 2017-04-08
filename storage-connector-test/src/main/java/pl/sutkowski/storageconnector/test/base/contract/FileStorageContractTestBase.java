package pl.sutkowski.storageconnector.test.base.contract;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import pl.sutkowski.api.FileHolder;
import pl.sutkowski.api.FileLocationHolder;
import pl.sutkowski.api.exception.FileStorageException;
import pl.sutkowski.storageconnector.test.base.AbstractTestBase;
import pl.sutkowski.storageconnector.test.utils.TmpDataContentProvider;

import java.nio.file.Paths;

public abstract class FileStorageContractTestBase extends AbstractTestBase {

    FileLocationHolder url;

    @After
    public void cleanUp() throws Exception {
        try {
            getFileStorageImplementor().remove(url);
        } catch (FileStorageException ex) {
            //This was intentionally left blank
        }
    }

    @Test
    public void shouldUploadFileAndDownloadIt() throws Exception {

        url = getFileStorageImplementor().upload(getTmpDataContent());
        FileHolder download = getFileStorageImplementor().download(url);

        Assertions.assertThat(download).isEqualTo(getTmpDataContent());
    }

    @Test
    public void shouldUploadFileInFolderAndDownloadIt() throws Exception {
        FileLocationHolder path = getFileStorageImplementor().produceFileLocationHolder(Paths.get("/path/file"));

        url = getFileStorageImplementor().upload(getTmpDataContent(), path);
        FileHolder download = getFileStorageImplementor().download(url);

        Assertions.assertThat(download).isEqualTo(getTmpDataContent());
    }

    @Test
    public void shouldReturnFileStorageExceptionWhenDownloadingNonExistingFile() throws Exception {

        url = getFileStorageImplementor().produceFileLocationHolder(Paths.get(""));

        thrown.expect(FileStorageException.class);
        getFileStorageImplementor().download(url);
    }

    @Test
    public void afterDeleteDownloadShouldReturnFileStorageException() throws Exception {

        url = getFileStorageImplementor().upload(getTmpDataContent());
        getFileStorageImplementor().remove(url);

        thrown.expect(FileStorageException.class);
        getFileStorageImplementor().download(url);
    }

    @Test
    public void shouldReturnErrorWhenAttemptingToRemoveFileWithEmptyNameAndPath() throws Exception {
        url = getFileStorageImplementor().produceFileLocationHolder(Paths.get(""));
        thrown.expect(FileStorageException.class);
        getFileStorageImplementor().remove(url);
    }

    @Test
    public void shouldReturnErrorWhenAttemptingToRemoveNonExistingFile() throws Exception {
        url = getFileStorageImplementor().produceFileLocationHolder(Paths.get("non-existing-file"));
        thrown.expect(FileStorageException.class);
        getFileStorageImplementor().remove(url);
    }


    @Test
    public void shouldMoveUploadedFile() throws Exception {
        FileLocationHolder path1 = getFileStorageImplementor().produceFileLocationHolder(Paths.get("/path1/file"));
        FileLocationHolder path2 = getFileStorageImplementor().produceFileLocationHolder(Paths.get("/path2/file"));
        url = getFileStorageImplementor().upload(getTmpDataContent(), path1);
        url = getFileStorageImplementor().move(url, path2);
        FileHolder download = getFileStorageImplementor().download(url);

        Assertions.assertThat(download).isEqualTo(getTmpDataContent());
    }

    private FileHolder getTmpDataContent() {
        return new TmpDataContentProvider().getContent();
    }

}
