package mnm.hdfontgen.pack.resource;

import mnm.hdfontgen.pack.ZipPath;

import java.io.IOException;
import java.nio.file.Path;

public interface Resource {
    ZipPath getPath();

    void writeTo(Path path) throws IOException;
}
