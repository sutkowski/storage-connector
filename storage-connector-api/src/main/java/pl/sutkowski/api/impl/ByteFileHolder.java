package pl.sutkowski.api.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sutkowski.api.FileHolder;

/**
 * Created by es on 20.04.16.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ByteFileHolder implements FileHolder {
    byte[] bytes;
}
