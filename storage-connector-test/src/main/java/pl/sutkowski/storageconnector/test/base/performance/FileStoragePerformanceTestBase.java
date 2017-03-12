package pl.sutkowski.storageconnector.test.base.performance;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Repeat;
import pl.sutkowski.api.FileHolder;
import pl.sutkowski.api.FileLocationHolder;
import pl.sutkowski.api.impl.ByteFileHolder;
import pl.sutkowski.storageconnector.test.base.AbstractTestBase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class FileStoragePerformanceTestBase extends AbstractTestBase {

    public static final int MB_IN_BYTES = 1024 * 1024;
    public static final int KB_IN_BYTES = 1024;
    public static final ByteFileHolder FILE_HOLDER_1_MB = new ByteFileHolder(new byte[1 * MB_IN_BYTES]);
    public static final ByteFileHolder FILE_HOLDER_2_MB = new ByteFileHolder(new byte[2 * MB_IN_BYTES]);
    public static final ByteFileHolder FILE_HOLDER_3_MB = new ByteFileHolder(new byte[3 * MB_IN_BYTES]);
    public static final ByteFileHolder FILE_HOLDER_4_MB = new ByteFileHolder(new byte[4 * MB_IN_BYTES]);
    public static final ByteFileHolder FILE_HOLDER_5_MB = new ByteFileHolder(new byte[5 * MB_IN_BYTES]);
    public static final ByteFileHolder FILE_HOLDER_10_MB = new ByteFileHolder(new byte[10 * MB_IN_BYTES]);
    public static final ByteFileHolder FILE_HOLDER_15_MB = new ByteFileHolder(new byte[15 * MB_IN_BYTES]);

    private final List<FileHolder> testFileHolders = Arrays.asList(
            new ByteFileHolder(new byte[128 * KB_IN_BYTES]),
            new ByteFileHolder(new byte[256 * KB_IN_BYTES]),
            new ByteFileHolder(new byte[512 * KB_IN_BYTES]),
            new ByteFileHolder(new byte[768 * KB_IN_BYTES]),
            FILE_HOLDER_1_MB
    );

    private Logger log = LoggerFactory.getLogger(FileStoragePerformanceTestBase.class);

    @Test
    @Repeat(value = 10)
    public void shouldCountAndLogUploadFileTime() {
        testFileHolders.stream().forEach(this::uploadFile);
    }

    @Test
    @Repeat(value = 10)
    public void shouldCountAndLogDownloadFileTime() {
        testFileHolders.stream().forEach(this::downloadFile);
    }

    @Test
    @Repeat(value = 10)
    public void shouldCountAndLogRemoveFileTime() {
        testFileHolders.stream().forEach(this::removeFile);
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
            String userHome = System.getProperty("user.home");
            String testsResultsFilename = String.format("%s/tests_results.txt", userHome);

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
