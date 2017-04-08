package pl.sutkowski.storageconnector.filesystem;

import org.junit.Test;
import pl.sutkowski.api.FileLocationHolder;
import pl.sutkowski.api.impl.PathFileLocationHolder;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class FileSystemFileStorageImplementorTest {

    @Test
    public void shouldResolveAbsolutePath() throws Exception {
        final String baseDirectory = "/tmp/";
        final FileSystemFileStorageImplementor fileSystemFileStorage = new FileSystemFileStorageImplementor(baseDirectory);

        PathFileLocationHolder url = new PathFileLocationHolder(Paths.get("/path"));
        final FileLocationHolder path = fileSystemFileStorage.resolveAbsolutePath(url);

        assertThat(path.getPath()).isEqualTo(Paths.get("/tmp/path"));
    }
}