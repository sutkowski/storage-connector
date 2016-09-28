package pl.sutkowski.storageconnector.test.base.performance;

import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.experimental.Builder;

@Builder
@Getter
public class TestExecutionData {

    private final String implementation;
    private final String testName;
    private final Long executionTime;
    private final Integer fileSize;

    public String getHeader() {
        return Arrays.asList(
                "implementation",
                "test name",
                "execution time (ms)",
                "file size (MB)",
                "file size (KB)",
                "file size (B)")
                .stream().collect(Collectors.joining(";")).concat("\n");
    }

    public String getData() {
        final Integer fileSizeKB = fileSize / 1024;
        final Integer fileSizeMB = fileSize / 1024 / 1024;
        return Arrays.asList(
                implementation,
                testName,
                executionTime.toString(),
                fileSizeMB.toString(),
                fileSizeKB.toString(),
                fileSize.toString())
                .stream().collect(Collectors.joining(";")).concat("\n");
    }

    public static TestExecutionData readFromFile(String fileLine){
        final String[] split = fileLine.split(";");
        return TestExecutionData.builder()
                .implementation(split[0])
                .testName(split[1])
                .executionTime(Integer.valueOf(split[2]).longValue())
                .fileSize(Integer.valueOf(split[5]))
                .build();

    }
}
