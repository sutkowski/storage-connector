package pl.sutkowski.api.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sutkowski.api.FileHolder;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ByteFileHolder implements FileHolder {
    byte[] bytes;
}
