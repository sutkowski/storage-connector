package pl.sutkowski.storageconnector.test.base.contract;

import java.nio.file.Paths;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import pl.sutkowski.api.FileHolder;
import pl.sutkowski.api.FileLocationHolder;
import pl.sutkowski.api.exception.FileStorageException;
import pl.sutkowski.api.impl.PathFileLocationHolder;
import pl.sutkowski.storageconnector.test.base.AbstractTestBase;

public abstract class FileStorageContractTestBase extends AbstractTestBase {

    FileLocationHolder url;

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
        FileHolder  download = getFileStorage().download(url);

        Assertions.assertThat(download).isEqualTo(getContent());
    }

    @Test
    public void shouldUploadFileInFolderAndDownloadIt() throws Exception {
        PathFileLocationHolder path = new PathFileLocationHolder(Paths.get("/path/file"));

        url = getFileStorage().upload(getContent(), path);
        FileHolder  download = getFileStorage().download(url);

        Assertions.assertThat(download).isEqualTo(getContent());
    }

    @Test
    public void shouldReturnFileStorageExceptionWhenDownloadingNonExistingFile() throws Exception {

        url = new PathFileLocationHolder(Paths.get(""));

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
        url = new PathFileLocationHolder(Paths.get(""));
        thrown.expect(FileStorageException.class);
        getFileStorage().remove(url);
    }


    @Test
    public void shouldMoveUploadedFile() throws Exception {
        PathFileLocationHolder path1 = new PathFileLocationHolder(Paths.get("/path1/file"));
        PathFileLocationHolder path2 = new PathFileLocationHolder(Paths.get("/path2/file"));
        url = getFileStorage().upload(getContent(),path1);
        url = getFileStorage().move(path1, path2);
        FileHolder  download = getFileStorage().download(url);

        Assertions.assertThat(download).isEqualTo(getContent());
    }

    private FileHolder getContent() {
        return getContentProvider().getContent();
    }

}
