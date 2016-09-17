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
import pl.sutkowski.api.impl.ByteFileHolder;
import pl.sutkowski.storageconnector.test.base.AbstractTestBase;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class FileStoragePerformanceTestBase extends AbstractTestBase {

    public static final int MB_IN_BYTES = 1024 * 1024;
    public static final ByteFileHolder FILE_HOLDER_1_MB = new ByteFileHolder(new byte[MB_IN_BYTES]);
    public static final ByteFileHolder FILE_HOLDER_5_MB = new ByteFileHolder(new byte[5 * MB_IN_BYTES]);
    public static final ByteFileHolder FILE_HOLDER_50_MB = new ByteFileHolder(new byte[50 * MB_IN_BYTES]);
    public static final ByteFileHolder FILE_HOLDER_150_MB = new ByteFileHolder(new byte[150 * MB_IN_BYTES]);
    public static final ByteFileHolder FILE_HOLDER_250_MB = new ByteFileHolder(new byte[250 * MB_IN_BYTES]);
    private Logger log = LoggerFactory.getLogger(FileStoragePerformanceTestBase.class);
    private String userHome;
    private String testsResultsFilename;
    private final List<FileHolder> testFileHolders = Arrays.asList(
            FILE_HOLDER_1_MB,
            FILE_HOLDER_5_MB,
            FILE_HOLDER_50_MB,
            FILE_HOLDER_150_MB,
            FILE_HOLDER_250_MB
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
            Files.write(Paths.get(testsResultsFilename), header.getBytes(), StandardOpenOption.CREATE);
            Files.write(Paths.get(testsResultsFilename), content.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.error("Unable to add execution record to file !", e);
        }
    }

}
