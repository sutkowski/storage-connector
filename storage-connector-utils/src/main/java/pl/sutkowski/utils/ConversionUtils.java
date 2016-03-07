package pl.sutkowski.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;

public class ConversionUtils {

    public static byte[] toByteArray(ByteArrayOutputStream outputStream) {
        return outputStream.toByteArray();
    }
}
