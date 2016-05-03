package pl.sutkowski.storageconnector.googledrive.impl.holder;

import com.google.api.services.drive.model.File;
import lombok.Data;
import pl.sutkowski.api.FileLocationHolder;

import java.nio.file.Path;
import java.nio.file.Paths;

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
