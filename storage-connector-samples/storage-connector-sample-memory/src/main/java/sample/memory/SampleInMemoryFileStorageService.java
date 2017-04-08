package sample.memory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sutkowski.api.FileHolder;
import pl.sutkowski.api.FileLocationHolder;
import pl.sutkowski.api.FileStorageImplementor;

@Service
public class SampleInMemoryFileStorageService {

    @Autowired
    FileStorageImplementor fileStorageImplementor;

    public FileHolder download(FileLocationHolder url) {
        return fileStorageImplementor.download(url);
    }

    public void remove(FileLocationHolder url) {
        fileStorageImplementor.remove(url);
    }

    public FileLocationHolder upload(FileHolder content) {
        return fileStorageImplementor.upload(content);
    }
}
