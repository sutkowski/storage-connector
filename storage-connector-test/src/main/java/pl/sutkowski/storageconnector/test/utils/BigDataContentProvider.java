package pl.sutkowski.storageconnector.test.utils;

import pl.sutkowski.api.FileHolder;
import pl.sutkowski.api.impl.ByteFileHolder;

public final class BigDataContentProvider
        implements ContentProvider {

    private FileHolder bytes = new ByteFileHolder(new byte[250 * 1024 * 1024]);

    @Override
    public FileHolder getContent() {
        return bytes;
    }

}
