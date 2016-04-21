package pl.sutkowski.storageconnector.googledrive.impl.holder;

import com.google.api.services.drive.model.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.Data;
import pl.sutkowski.api.FileLocationHolder;

@Data
public class GoogleDriveFileLocationHolder implements FileLocationHolder {

    File file;

    public GoogleDriveFileLocationHolder(File file) {
        this.file = file;
    }

    @Override
    public Path getPath() {
        return Paths.get(file.getId());
    }
}
