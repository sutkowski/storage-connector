package sample.memory;

import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sutkowski.api.FileStorage;

@Service
public class SampleInMemoryFileStorageService {

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
