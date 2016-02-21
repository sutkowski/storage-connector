package pl.sutkowski.storageconnector.test.utils;

public final class TmpDataContentProvider
        implements ContentProvider {

    @Override
    public byte[] getContent() {
        return "tmp".getBytes();
    }

}
