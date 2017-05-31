package pl.sutkowski.api;

import java.util.List;
import java.util.Map;

public class FileStorageImplementation implements FileStorage {

    private final FileStorageImplementor storageImplementor;

    public FileStorageImplementation(FileStorageImplementor storageImplementor) {
        this.storageImplementor = storageImplementor;
    }

    @Override
    public FileLocationHolder upload(FileHolder content) {
        return storageImplementor.upload(content);
    }

    @Override
    public FileLocationHolder upload(FileHolder content, FileLocationHolder url) {
        return storageImplementor.upload(content, url);
    }

    @Override
    public List<FileLocationHolder> batchUpload(Map<FileLocationHolder, FileHolder> files) {
        return storageImplementor.batchUpload(files);
    }

    @Override
    public List<FileLocationHolder> batchUpload(List<FileHolder> files) {
        return storageImplementor.batchUpload(files);
    }

    @Override
    public FileHolder download(FileLocationHolder url) {
        return storageImplementor.download(url);
    }

    @Override
    public void remove(FileLocationHolder url) {
        storageImplementor.remove(url);
    }

    @Override
    public FileLocationHolder move(FileLocationHolder fromPath, FileLocationHolder toPath) {
        return storageImplementor.move(fromPath, toPath);
    }
}
