package pl.sutkowski.storageconnector.test.base.performance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import org.assertj.core.util.Lists;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Repeat;
import pl.sutkowski.api.FileHolder;
import pl.sutkowski.api.FileLocationHolder;
import pl.sutkowski.api.impl.ByteFileHolder;
import pl.sutkowski.storageconnector.test.base.AbstractTestBase;

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
    public static final int TIMES = 10;

    private final List<FileHolder> testFileHolders = Arrays.asList(
            new ByteFileHolder(new byte[128 * KB_IN_BYTES]),
            new ByteFileHolder(new byte[256 * KB_IN_BYTES]),
            new ByteFileHolder(new byte[512 * KB_IN_BYTES]),
            new ByteFileHolder(new byte[768 * KB_IN_BYTES]),
            FILE_HOLDER_1_MB
    );

    private Logger log = LoggerFactory.getLogger(FileStoragePerformanceTestBase.class);

    @Test
    @Ignore
    public void shouldCountAndLogUploadFileTime() {
        testFileHolders.stream().forEach(this::uploadFile);
    }

    @Test
    @Ignore
    public void shouldCountAndLogDownloadFileTime() {
        testFileHolders.stream().forEach(this::downloadFile);
    }

    @Test
    @Ignore
    public void shouldCountAndLogRemoveFileTime() {
        testFileHolders.stream().forEach(this::removeFile);
    }

    @Test
    @Repeat(TIMES)
    @Ignore
    public void shouldCountAndLogUploadFileSequentiallyAndBatchUploadSameSmallFiles() {
        List<FileHolder> fileHolders = Lists.newArrayList();
        IntStream.range(0, 10).forEach((run) -> fileHolders.add(ByteFileHolder.of("test")));

        uploadFilesAndLogAccumulatedTime(fileHolders);
        batchUploadFilesAndLogAccumulatedTime(fileHolders);
    }

    @Test
    @Repeat(TIMES)
    @Ignore
    public void shouldCountAndLogUploadFileSequentiallyAndBatchUploadSameFiles() {
        List<FileHolder> fileHolders = Lists.newArrayList();
        IntStream.range(0, 10).forEach((run) -> fileHolders.add(FILE_HOLDER_1_MB));

        uploadFilesAndLogAccumulatedTime(fileHolders);
        batchUploadFilesAndLogAccumulatedTime(fileHolders);
    }

    @Test
    @Repeat(TIMES)
    @Ignore
    public void shouldCountAndLogUploadFileAndBatchUploadFilesOfSameSize() {
        List<FileHolder> fileHolders = Lists.newArrayList();
        int filesCount = 10;
        IntStream.range(0, filesCount).forEach((run) -> fileHolders.add(FILE_HOLDER_1_MB));

        uploadFilesAndLogAccumulatedTime(Collections.singletonList(new ByteFileHolder(new byte[filesCount * MB_IN_BYTES])));
        batchUploadFilesAndLogAccumulatedTime(fileHolders);
    }

    private void uploadFilesAndLogAccumulatedTime(List<FileHolder> fileHolders) {
        final long beginTime = System.currentTimeMillis();
        fileHolders.stream().forEach((fileHolder) -> getFileStorageImplementor().upload(fileHolder));
        final long endTime = System.currentTimeMillis();
        logExecutionTime(
                endTime - beginTime,
                getFilesTotalSize(fileHolders),
                String.format("upload %s times [%s]", fileHolders.size(),
                        fileHolders.stream().map(FileHolder::getBytes)
                                .map(bytes -> String.valueOf(bytes.length))
                                .reduce((a, b) -> a + "," + b).get()),
                "%s/batch_tests_results.txt");
    }

    private void batchUploadFilesAndLogAccumulatedTime(List<FileHolder> fileHolders) {
        final long beginTime = System.currentTimeMillis();
        getFileStorageImplementor().batchUpload(fileHolders);
        final long endTime = System.currentTimeMillis();
        logExecutionTime(
                endTime - beginTime,
                getFilesTotalSize(fileHolders),
                String.format("batch upload %s files [%s]", fileHolders.size(),
                        fileHolders.stream().map(FileHolder::getBytes)
                                .map(bytes -> String.valueOf(bytes.length))
                                .reduce((a, b) -> a + "," + b).get()),
                "%s/batch_tests_results.txt");
    }

    private Integer getFilesTotalSize(List<FileHolder> fileHolders) {
        return fileHolders.stream().map(FileHolder::getBytes).map(bytes -> bytes.length).reduce(0, (a, b) -> a + b);
    }

    private void uploadFile(FileHolder fileHolder) {
        final long beginTime = System.currentTimeMillis();

        getFileStorageImplementor().upload(fileHolder);

        final long endTime = System.currentTimeMillis();
        logExecutionTime(endTime - beginTime, fileHolder.getBytes().length, "upload");
    }

    private void downloadFile(FileHolder fileHolder) {
        final FileLocationHolder upload = getFileStorageImplementor().upload(fileHolder);

        final long beginTime = System.currentTimeMillis();

        final FileHolder download = getFileStorageImplementor().download(upload);

        final long endTime = System.currentTimeMillis();
        logExecutionTime(endTime - beginTime, fileHolder.getBytes().length, "download");

        assertThat(download).isEqualTo(fileHolder);
    }

    private void removeFile(FileHolder fileHolder) {
        final FileLocationHolder upload = getFileStorageImplementor().upload(fileHolder);

        final long beginTime = System.currentTimeMillis();

        getFileStorageImplementor().remove(upload);

        final long endTime = System.currentTimeMillis();
        logExecutionTime(endTime - beginTime, fileHolder.getBytes().length, "remove");
    }

    private void logExecutionTime(Long nanos, Integer fileSize, String testName) {
        logExecutionTime(nanos, fileSize, testName, "%s/tests_results.txt");
    }

    private void logExecutionTime(Long nanos, Integer fileSize, String testName, String format) {
        try {
            String userHome = System.getProperty("user.home");
            String testsResultsFilename = String.format(format, userHome);

            final TestExecutionData executionData = TestExecutionData.builder()
                    .executionTime(nanos)
                    .fileSize(fileSize)
                    .implementation(getFileStorageImplementor().getClass().getSimpleName())
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
