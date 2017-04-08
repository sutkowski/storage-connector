package pl.sutkowski.api;

public interface FileStorage {

    FileLocationHolder upload(FileHolder content);

    FileLocationHolder upload(FileHolder content, FileLocationHolder url);

    FileHolder download(FileLocationHolder url);

    void remove(FileLocationHolder url);

    FileLocationHolder move(FileLocationHolder fromPath, FileLocationHolder toPath);
}
