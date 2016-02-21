package pl.sutkowski.storageconnector.test.utils;

public final class BigDataContentProvider
        implements ContentProvider {

    private byte[] bytes = new byte[250 * 1024 * 1024];

    @Override
    public byte[] getContent() {
        return bytes;
    }

}
