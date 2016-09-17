package pl.sutkowski.storageconnector.test.base.performance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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

    @Test
    public void shouldCountAndLogUploadFileTime() throws IOException {
        final long beginTime = System.currentTimeMillis();

        final FileHolder bigDataContent = getBigDataContent();
        getFileStorage().upload(bigDataContent);

        final long endTime = System.currentTimeMillis();
        logExecutionTime(endTime - beginTime, bigDataContent.getBytes().length, "upload");
    }

    @Test
    public void shouldCountAndLogDownloadFileTime() throws IOException {
        final FileHolder bigDataContent = getBigDataContent();
        final FileLocationHolder upload = getFileStorage().upload(bigDataContent);

        final long beginTime = System.currentTimeMillis();

        final FileHolder download = getFileStorage().download(upload);

        final long endTime = System.currentTimeMillis();
        logExecutionTime(endTime - beginTime, bigDataContent.getBytes().length, "download");

        assertThat(download).isEqualTo(bigDataContent);
    }

    @Test
    public void shouldCountAndLogRemoveFileTime() throws IOException {
        final FileHolder bigDataContent = getBigDataContent();
        final FileLocationHolder upload = getFileStorage().upload(bigDataContent);

        final long beginTime = System.currentTimeMillis();

        getFileStorage().remove(upload);

        final long endTime = System.currentTimeMillis();
        logExecutionTime(endTime - beginTime, bigDataContent.getBytes().length, "remove");
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

    private FileHolder getBigDataContent() {
        return new BigDataContentProvider().getContent();
    }

}
