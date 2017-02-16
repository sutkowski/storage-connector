package pl.sutkowski.api;

import java.nio.file.Path;

public interface FileLocationHolder {

    Path getPath();

    default String getPathAsString() {
        return getPath().toString();
    }

}
