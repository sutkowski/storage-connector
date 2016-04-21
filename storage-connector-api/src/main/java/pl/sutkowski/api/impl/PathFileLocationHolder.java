package pl.sutkowski.api.impl;

import java.nio.file.Path;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sutkowski.api.FileLocationHolder;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PathFileLocationHolder implements FileLocationHolder{
    Path path;
}
