package pl.sutkowski.storageconnector.test.utils;

import pl.sutkowski.api.FileHolder;
import pl.sutkowski.api.impl.ByteFileHolder;

public final class TmpDataContentProvider
        implements ContentProvider {

    @Override
    public FileHolder getContent() {
        return new ByteFileHolder("tmp".getBytes());
    }

}
