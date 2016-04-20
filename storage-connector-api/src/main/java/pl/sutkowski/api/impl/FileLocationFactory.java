package pl.sutkowski.api.impl;

import java.nio.file.Path;
import pl.sutkowski.api.FileLocationHolder;

/**
 * Created by es on 20.04.16.
 */
public class FileLocationFactory {
    public static FileLocationHolder of(Path url) {
        return new PathFileLocationHolder(url);
    }
}
