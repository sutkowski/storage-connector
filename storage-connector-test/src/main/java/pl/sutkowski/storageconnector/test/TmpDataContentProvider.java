package pl.sutkowski.storageconnector.test;

/**
 * @author eS
 */
public final class TmpDataContentProvider
        implements ContentProvider {

    @Override
    public byte[] getContent() {
        return "tmp".getBytes();
    }

}
