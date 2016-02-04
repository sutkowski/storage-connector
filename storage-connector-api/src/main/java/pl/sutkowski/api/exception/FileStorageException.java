/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.sutkowski.api.exception;

import java.nio.file.Path;

/**
 *
 * @author eS
 */
public class FileStorageException
        extends RuntimeException {

    private static final String FILE_NOT_FOUND = "File not found : %s";
    private static final String UPLOAD_FAILED = "Upload failed";

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

}
