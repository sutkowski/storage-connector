package pl.sutkowski.api.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StreamUtils;
import pl.sutkowski.api.exception.FileStorageException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileStorageUtils {

    public static final String TMP_DIRECTORY = "/tmp/";

    public static byte[] toByteArray(ByteArrayOutputStream outputStream) {
        return outputStream.toByteArray();
    }

    public static File toTmpFile(byte[] content) {
        String tmpFilePath = TMP_DIRECTORY + UUID.randomUUID().toString();
        Path path = Paths.get(tmpFilePath);
        createDirectoryIfNotExists(path.getParent());
        try {
            Files.write(path, content);
        } catch (IOException e) {
            throw FileStorageException.systemException();
        }
        return new File(tmpFilePath);
    }

    public static void removeTmpFile(File file) {
        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            throw FileStorageException.removeFailed(e);
        }
    }

    public static byte[] toByteArray(InputStream inputStream) {
        try {
            return StreamUtils.copyToByteArray(inputStream);
        } catch (IOException e) {
            throw FileStorageException.systemException();
        }
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
