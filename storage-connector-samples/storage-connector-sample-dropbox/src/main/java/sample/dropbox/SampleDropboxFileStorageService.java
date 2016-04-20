package sample.dropbox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sutkowski.api.FileHolder;
import pl.sutkowski.api.FileLocationHolder;
import pl.sutkowski.api.FileStorage;

@Service
public class SampleDropboxFileStorageService {

    @Autowired
    FileStorage fileStorage;

    public FileHolder download(FileLocationHolder url) {
        return fileStorage.download(url);
    }

    public void remove(FileLocationHolder url) {
        fileStorage.remove(url);
    }

    public FileLocationHolder upload(FileHolder content) {
        return fileStorage.upload(content);
    }
}
