package pl.sutkowski.storageconnector.dropbox;

import java.nio.file.Path;
import pl.sutkowski.api.FileStorage;

public interface DropboxFileStorage extends FileStorage {

    Path createFolder(Path url);

}
