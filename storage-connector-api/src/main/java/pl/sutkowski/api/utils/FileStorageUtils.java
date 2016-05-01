package pl.sutkowski.api.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StreamUtils;
import pl.sutkowski.api.exception.FileStorageException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileStorageUtils {

    public static final String TMP_DIRECTORY = "/tmp/";

    public static byte[] toByteArray(ByteArrayOutputStream outputStream) {
        return outputStream.toByteArray();
    }

    public static File toTmpFile(byte[] content) throws IOException {
        String tmpFilePath = TMP_DIRECTORY + UUID.randomUUID().toString();
        Path path = Paths.get(tmpFilePath);
        createDirectoryIfNotExists(path.getParent());
        Files.write(path, content);
        return new File(tmpFilePath);
    }

    public static void removeTmpFile(File file) throws IOException {
        Files.delete(file.toPath());
    }

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        return StreamUtils.copyToByteArray(inputStream);
    }

    public static void createDirectoryIfNotExists(Path parent) {
        if (!Files.exists(parent)) {
            try {
                Files.createDirectories(parent);
            } catch (IOException e) {
                throw FileStorageException.pathNotFound();
            }
        }
    }
}
