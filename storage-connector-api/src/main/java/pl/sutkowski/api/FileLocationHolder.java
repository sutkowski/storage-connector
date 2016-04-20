package pl.sutkowski.api;

import java.nio.file.Path;

/**
 * Created by es on 20.04.16.
 */
public interface FileLocationHolder {

    Path getPath();

    static FileLocationHolder of(Path url) {
        return null;
    }
}
