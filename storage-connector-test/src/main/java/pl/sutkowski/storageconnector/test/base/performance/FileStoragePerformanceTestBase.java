package pl.sutkowski.storageconnector.test.base.performance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sutkowski.api.FileHolder;
import pl.sutkowski.storageconnector.test.base.AbstractTestBase;
import pl.sutkowski.storageconnector.test.utils.BigDataContentProvider;

public abstract class FileStoragePerformanceTestBase extends AbstractTestBase {

    private Logger log = LoggerFactory.getLogger(FileStoragePerformanceTestBase.class);
    private String userHome;
    private String testsResultsFilename;
    @Rule
    public Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void succeeded(long nanos, Description description) {
            super.succeeded(nanos, description);
            try {
                userHome = System.getProperty("user.home");
                testsResultsFilename = String.format("%s/tests_results.txt", userHome);

                SortedMap<String, String> testResults = new TreeMap<>();

                testResults.put("FileStorageImplementation", getFileStorage().getClass().getCanonicalName());
                testResults.put("executionTime", String.valueOf(nanos));
                testResults.put("fileSize(bytes)", String.valueOf(getBigDataContentContent().getBytes().length));

                final String header = testResults.keySet().stream().collect(Collectors.joining(";")).concat("\n");
                final String content = testResults.values().stream().collect(Collectors.joining(";")).concat("\n");
                Files.write(Paths.get(testsResultsFilename), header.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                Files.write(Paths.get(testsResultsFilename), content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException e) {
                log.error("Unable to add execution record to file !", e);
            }
        }
    };

    @Test
    public void shouldUploadFile() throws IOException {
        getFileStorage().upload(getBigDataContentContent());
    }

    private FileHolder getBigDataContentContent() {
        return new BigDataContentProvider().getContent();
    }

}
