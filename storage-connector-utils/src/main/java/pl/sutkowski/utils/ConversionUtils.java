package pl.sutkowski.utils;

import java.io.ByteArrayOutputStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConversionUtils {

    public static byte[] toByteArray(ByteArrayOutputStream outputStream) {
        return outputStream.toByteArray();
    }
}
