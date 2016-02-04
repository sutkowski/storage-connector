/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.sutkowski.storageconnector.filesystem;

import pl.sutkowski.api.FileStorage;
import pl.sutkowski.api.FileStorageProvider;

import java.io.IOException;

/**
 * @author eS
 */
public final class FileSystemFileStorageProvider
        implements FileStorageProvider {

    @Override
    public FileStorage getFileStorage() throws IOException {
        return new FileSystemFileStorage("/home/tmp/");
    }

}
