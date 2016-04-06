package pl.sutkowski.api.exception;

import java.nio.file.Path;

public class FileStorageException
        extends RuntimeException {

    private static final String FILE_NOT_FOUND = "File not found: %s";
    private static final String UPLOAD_FAILED = "Upload failed";
    private static final String CREATE_FOLDER = "Create folder %s failed";
    private static final String MOVE_FAILED = "Failed to move file from: %s, to: %s";
    private static final String PATH_NOT_FOUND_OR_EMPTY = "Path not found or empty";

    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(Throwable cause) {
        super(cause);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public static FileStorageException fileNotFound(Path url) {
        return new FileStorageException(String.format(FILE_NOT_FOUND, url));
    }

    public static FileStorageException uploadFailed(Throwable cause) {
        return new FileStorageException(UPLOAD_FAILED, cause);
    }

    public static FileStorageException moveException(String fromPath, String toPath) {
        return new FileStorageException(String.format(MOVE_FAILED, fromPath, toPath));
    }

    public static FileStorageException createFolder(String locationPath) {
        return new FileStorageException(String.format(CREATE_FOLDER, locationPath));
    }

    public static FileStorageException pathNotFound() {
        return new FileStorageException(PATH_NOT_FOUND_OR_EMPTY);
    }
}
