package pl.sutkowski.api.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.sutkowski.api.FileHolder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ByteFileHolder implements FileHolder {
    byte[] bytes;
}
