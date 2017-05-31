package pl.sutkowski.storageconnector.test.base.contract;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Test;
import pl.sutkowski.api.FileHolder;
import pl.sutkowski.api.FileLocationHolder;
import pl.sutkowski.api.exception.FileStorageException;
import pl.sutkowski.api.impl.ByteFileHolder;
import pl.sutkowski.storageconnector.test.base.AbstractTestBase;
import pl.sutkowski.storageconnector.test.utils.TmpDataContentProvider;

import java.nio.file.Paths;
import java.util.*;

import static java.util.stream.Collectors.toList;

public abstract class FileStorageContractTestBase extends AbstractTestBase {

    Collection<FileLocationHolder> urls = Lists.newArrayList();
    FileLocationHolder url;

    @After
    public void cleanUp() throws Exception {
        removeUrl(url);
        url = null;

        urls.stream().forEach(url -> removeUrl(url));
        urls.clear();
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
    public void shouldUploadFileInFolderUsingBatchSaveAndDownloadIt() throws Exception {
        Map<FileLocationHolder, FileHolder> files = Collections.singletonMap(
                getFileStorageImplementor().produceFileLocationHolder(Paths.get("/path/file")), getTmpDataContent());
        List<FileLocationHolder> fileLocationHolders = getFileStorageImplementor().batchUpload(files);

        Assertions.assertThat(fileLocationHolders).hasSize(1);
        url = fileLocationHolders.get(0);

        FileHolder download = getFileStorageImplementor().download(url);

        Assertions.assertThat(download).isEqualTo(getTmpDataContent());
    }

    @Test
    public void shouldUploadFileUsingBatchSaveAndDownloadIt() throws Exception {
        List<FileHolder> files = Collections.singletonList(getTmpDataContent());
        List<FileLocationHolder> fileLocationHolders = getFileStorageImplementor().batchUpload(files);

        Assertions.assertThat(fileLocationHolders).hasSize(1);
        url = fileLocationHolders.get(0);

        FileHolder download = getFileStorageImplementor().download(url);

        Assertions.assertThat(download).isEqualTo(getTmpDataContent());
    }

    @Test
    public void shouldUploadFilesInFolderUsingBatchSaveAndDownloadThem() throws Exception {

        Map<FileLocationHolder, FileHolder> files = new HashMap<>();
        FileHolder tmpDataContent1 = ByteFileHolder.of("Some test data");
        FileHolder tmpDataContent2 = ByteFileHolder.of("Another test data");
        files.put(getFileStorageImplementor().produceFileLocationHolder(Paths.get("/path/file1")), tmpDataContent1);
        files.put(getFileStorageImplementor().produceFileLocationHolder(Paths.get("/path/file2")), tmpDataContent2);

        List<FileLocationHolder> urls = getFileStorageImplementor().batchUpload(files);

        Assertions.assertThat(urls).hasSize(2);

        Assertions.assertThat(
                urls.stream().map(url -> getFileStorageImplementor().download(url)).collect(toList()))
                .containsOnly(tmpDataContent1, tmpDataContent2);
    }

    @Test
    public void shouldUploadFilesUsingBatchSaveAndDownloadThem() throws Exception {
        FileHolder tmpDataContent1 = ByteFileHolder.of("Some test data");
        FileHolder tmpDataContent2 = ByteFileHolder.of("Another test data");
        List<FileHolder> files = Lists.newArrayList();
        files.add(tmpDataContent1);
        files.add(tmpDataContent2);

        List<FileLocationHolder> urls = getFileStorageImplementor().batchUpload(files);

        Assertions.assertThat(urls).hasSize(2);

        Assertions.assertThat(
                urls.stream().map(url -> getFileStorageImplementor().download(url)).collect(toList()))
                .containsOnly(tmpDataContent1, tmpDataContent2);
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

    private void removeUrl(FileLocationHolder url) {
        try {
            getFileStorageImplementor().remove(url);
        } catch (FileStorageException ex) {
            //This was intentionally left blank
        }
    }

    private FileHolder getTmpDataContent() {
        return new TmpDataContentProvider().getContent();
    }

}
