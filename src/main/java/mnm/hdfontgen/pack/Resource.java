package mnm.hdfontgen.pack;

import java.io.IOException;
import java.nio.file.Path;

public interface Resource {
    ZipPath getPath();

    void writeTo(Path path) throws IOException;
}
