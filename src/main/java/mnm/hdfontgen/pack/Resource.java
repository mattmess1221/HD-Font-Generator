package mnm.hdfontgen.pack;

import java.io.IOException;
import java.nio.file.Path;

public interface Resource {
    String getPath();

    void writeTo(Path path) throws IOException;
}
