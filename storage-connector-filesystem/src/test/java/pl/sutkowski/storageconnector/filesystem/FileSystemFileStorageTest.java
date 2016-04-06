package pl.sutkowski.storageconnector.filesystem;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FileSystemFileStorageTest {

    @Test
    public void shouldResolveAbsolutePath() throws Exception {
        final String baseDirectory = "/home/user/tmp/";
        final FileSystemFileStorage fileSystemFileStorage = new FileSystemFileStorage(baseDirectory);

        Path url = Paths.get("/path");
        final Path path = fileSystemFileStorage.resolveAbsolutePath(url);

        assertThat(path).isEqualTo(Paths.get("/home/user/tmp/path"));
    }
}