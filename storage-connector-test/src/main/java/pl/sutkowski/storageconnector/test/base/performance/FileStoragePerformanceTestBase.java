package pl.sutkowski.storageconnector.test.base.performance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sutkowski.api.FileHolder;
import pl.sutkowski.api.FileLocationHolder;
import pl.sutkowski.storageconnector.test.base.AbstractTestBase;
import pl.sutkowski.storageconnector.test.utils.BigDataContentProvider;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class FileStoragePerformanceTestBase extends AbstractTestBase {

    private Logger log = LoggerFactory.getLogger(FileStoragePerformanceTestBase.class);
    private String userHome;
    private String testsResultsFilename;
    private final List<FileHolder> testFileHolders = Arrays.asList(
            new BigDataContentProvider().getContent()
    );

    @Test
    public void shouldCountAndLogUploadFileTime() {
        testFileHolders.stream().forEach(fileHolder -> uploadFile(fileHolder));
    }

    @Test
    public void shouldCountAndLogDownloadFileTime() {
        testFileHolders.stream().forEach(fileHolder -> downloadFile(fileHolder));
    }

    @Test
    public void shouldCountAndLogRemoveFileTime() {
        testFileHolders.stream().forEach(fileHolder -> removeFile(fileHolder));
    }

    private void uploadFile(FileHolder fileHolder) {
        final long beginTime = System.currentTimeMillis();

        getFileStorage().upload(fileHolder);

        final long endTime = System.currentTimeMillis();
        logExecutionTime(endTime - beginTime, fileHolder.getBytes().length, "upload");
    }

    private void downloadFile(FileHolder fileHolder) {
        final FileLocationHolder upload = getFileStorage().upload(fileHolder);

        final long beginTime = System.currentTimeMillis();

        final FileHolder download = getFileStorage().download(upload);

        final long endTime = System.currentTimeMillis();
        logExecutionTime(endTime - beginTime, fileHolder.getBytes().length, "download");

        assertThat(download).isEqualTo(fileHolder);
    }

    private void removeFile(FileHolder fileHolder) {
        final FileLocationHolder upload = getFileStorage().upload(fileHolder);

        final long beginTime = System.currentTimeMillis();

        getFileStorage().remove(upload);

        final long endTime = System.currentTimeMillis();
        logExecutionTime(endTime - beginTime, fileHolder.getBytes().length, "remove");
    }

    private void logExecutionTime(Long nanos, Integer fileSize, String testName) {
        try {
            userHome = System.getProperty("user.home");
            testsResultsFilename = String.format("%s/tests_results.txt", userHome);

            final TestExecutionData executionData = TestExecutionData.builder()
                    .executionTime(nanos)
                    .fileSize(fileSize)
                    .implementation(getFileStorage().getClass().getSimpleName())
                    .testName(testName)
                    .build();

            final String header = executionData.getHeader();
            final String content = executionData.getData();
            Files.write(Paths.get(testsResultsFilename), header.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            Files.write(Paths.get(testsResultsFilename), content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.error("Unable to add execution record to file !", e);
        }
    }

}
