package pl.sutkowski.storageconnector.test.base.performance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import org.junit.Ignore;
import org.junit.Test;

import static java.util.stream.Collectors.toList;

@Ignore("used just to parse performance test output to be usable in plotter")
public class PerformanceTestsOutputProcessor {

    @Test
    public void transformFileToCorrectFormattedCsv() throws IOException {

        String userHome = System.getProperty("user.home");
        String testsResultsFilename = String.format("%s/batch_tests_results.txt", userHome);
        String testsResultsOutputCsv = String.format("%s/batch_tests_results.csv", userHome);

        final List<String> strings = Files.readAllLines(Paths.get(testsResultsFilename));

        final List<TestExecutionData> testExecutionData = strings.stream().skip(1)
                .map(str -> TestExecutionData.readFromFile(str))
                .collect(toList());
        final List<String> implementations =
                testExecutionData.stream().map(TestExecutionData::getImplementation).distinct().collect(toList());
        final List<String> tests =
                testExecutionData.stream().map(TestExecutionData::getTestName).distinct().collect(toList());
        final List<Integer> fileSizes =
                testExecutionData.stream().map(TestExecutionData::getFileSize).distinct().sorted().collect(toList());

        StringBuilder outputData = new StringBuilder();
        outputData.append("\t\t");
        outputData.append(
                fileSizes.stream().map(size -> size / 1024.0 / 1024.0).map(size -> size.toString())
                        .collect(Collectors.joining("\t")).concat("\n"));
        tests.stream().forEach(
                test -> implementations.stream().forEach(
                        implementation -> {
                            outputData.append(String.format("[%s]\t%s\t", test, implementation));
                            fileSizes.stream().forEach(
                                    fileSize -> {
                                        final OptionalDouble averageExecutionTime = testExecutionData.stream()
                                                .filter(ted -> ted.getTestName().equalsIgnoreCase(test))
                                                .filter(ted -> ted.getImplementation().equalsIgnoreCase(implementation))
                                                .filter(ted -> ted.getFileSize().equals(fileSize))
                                                .map(TestExecutionData::getExecutionTime)
                                                .mapToInt(ted -> ted.intValue())
                                                .average();
                                        averageExecutionTime.ifPresent(averageTime ->
                                                outputData.append(averageTime + "\t"));
                                    }
                            );
                            outputData.append("\n");
                        }
                )
        );
        System.err.println(outputData.toString());
        Files.write(Paths.get(testsResultsOutputCsv), outputData.toString().getBytes(), StandardOpenOption.CREATE);
    }


}
