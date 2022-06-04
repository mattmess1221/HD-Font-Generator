package mnm.hdfontgen.pack.resource;

import java.io.IOException;
import java.nio.file.Path;

import mnm.hdfontgen.pack.ZipPath;

public interface Resource {
    ZipPath getPath();

    void writeTo(Path path) throws IOException;

    /**
     * The order of processing. Lower ordered resources will run before higher
     * ordered ones.
     * @return The order
     */
    default int order() {
        return 0;
    }
}
