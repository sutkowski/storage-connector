/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.sutkowski.storageconnector.memory;

import pl.sutkowski.api.FileStorage;
import pl.sutkowski.api.FileStorageProvider;

/**
 * @author eS
 */
public final class InMemoryFileStorageProvider
        implements FileStorageProvider {

    @Override
    public FileStorage getFileStorage() {
        return new InMemoryFileStorage();
    }

}
