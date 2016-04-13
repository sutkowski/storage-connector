package pl.sutkowski.api.exception;

public class FileStorageInitializationException
        extends RuntimeException {

    public FileStorageInitializationException(String message) {
        super(message);
    }

    public FileStorageInitializationException(Throwable cause) {
        super(cause);
    }

    public FileStorageInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

}
