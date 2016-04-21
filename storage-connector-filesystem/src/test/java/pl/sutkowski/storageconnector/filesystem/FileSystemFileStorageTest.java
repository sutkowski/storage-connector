package pl.sutkowski.storageconnector.filesystem;

import java.nio.file.Paths;
import org.junit.Test;
import pl.sutkowski.api.FileLocationHolder;
import pl.sutkowski.api.impl.PathFileLocationHolder;

import static org.assertj.core.api.Assertions.assertThat;

public class FileSystemFileStorageTest {

    @Test
    public void shouldResolveAbsolutePath() throws Exception {
        final String baseDirectory = "/tmp/";
        final FileSystemFileStorage fileSystemFileStorage = new FileSystemFileStorage(baseDirectory);

        PathFileLocationHolder url = new PathFileLocationHolder(Paths.get("/path"));
        final FileLocationHolder path = fileSystemFileStorage.resolveAbsolutePath(url);

        assertThat(path.getPath()).isEqualTo(Paths.get("/tmp/path"));
    }
}