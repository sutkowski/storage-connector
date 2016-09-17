package pl.sutkowski.storageconnector.test.base.performance;

import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.experimental.Builder;

@Builder
public class TestExecutionData {

    private final String implementation;
    private final String testName;
    private final Long executionTime;
    private final Integer fileSize;

    public String getHeader() {
        return Arrays.asList("implementation","test name","execution time (ms)", "file size (bytes)")
                .stream().collect(Collectors.joining(" ; ")).concat("\n");
    }
    public String getData() {
        return Arrays.asList(implementation,testName,executionTime.toString(), fileSize.toString())
                .stream().collect(Collectors.joining(" ; ")).concat("\n");
    }
}
