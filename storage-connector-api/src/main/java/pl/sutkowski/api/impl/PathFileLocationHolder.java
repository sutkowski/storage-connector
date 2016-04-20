package pl.sutkowski.api.impl;

import java.nio.file.Path;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sutkowski.api.FileLocationHolder;

/**
 * Created by es on 20.04.16.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PathFileLocationHolder implements FileLocationHolder{
    Path path;
}
