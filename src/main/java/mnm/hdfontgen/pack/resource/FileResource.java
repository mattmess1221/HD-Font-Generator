package mnm.hdfontgen.pack.resource;

import mnm.hdfontgen.pack.ZipPath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A resource which copies the input into the output.
 */
public class FileResource implements Resource {

    private final Path input;
    private final ZipPath output;

    /**
     * Create a new FileResource.
     *
     * @param input  The input file
     * @param output The zip path to the output file
     */
    public FileResource(Path input, ZipPath output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public ZipPath getPath() {
        return output;
    }

    @Override
    public void writeTo(Path path) throws IOException {
        Files.copy(this.input, path);
    }
}
