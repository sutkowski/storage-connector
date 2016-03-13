package sample.dropbox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sutkowski.api.FileStorage;

import java.nio.file.Path;

@Service
public class SampleDropboxFileStorageService {

    @Autowired
    FileStorage fileStorage;

    public byte[] download(Path url) {
        return fileStorage.download(url);
    }

    public void remove(Path url) {
        fileStorage.remove(url);
    }

    public Path upload(byte[] content) {
        return fileStorage.upload(content);
    }
}
