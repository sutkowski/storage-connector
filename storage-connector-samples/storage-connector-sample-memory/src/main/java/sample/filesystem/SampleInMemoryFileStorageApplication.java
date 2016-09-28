package sample.filesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SampleInMemoryFileStorageApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleInMemoryFileStorageApplication.class, args);
    }
}
