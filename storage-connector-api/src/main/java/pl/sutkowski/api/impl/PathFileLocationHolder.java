package pl.sutkowski.api.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sutkowski.api.FileLocationHolder;

import java.nio.file.Path;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PathFileLocationHolder implements FileLocationHolder{
    Path path;
}
