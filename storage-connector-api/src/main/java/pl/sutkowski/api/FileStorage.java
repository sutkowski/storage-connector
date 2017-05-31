package pl.sutkowski.api;

import java.util.List;
import java.util.Map;

public interface FileStorage {

    FileLocationHolder upload(FileHolder content);

    FileLocationHolder upload(FileHolder content, FileLocationHolder url);

    List<FileLocationHolder> batchUpload(List<FileHolder> files);

    List<FileLocationHolder> batchUpload(Map<FileLocationHolder, FileHolder> files);

    FileHolder download(FileLocationHolder url);

    void remove(FileLocationHolder url);

    FileLocationHolder move(FileLocationHolder fromPath, FileLocationHolder toPath);
}
