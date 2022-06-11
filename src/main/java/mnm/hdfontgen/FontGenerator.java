package mnm.hdfontgen;

import java.io.IOException;
import java.io.UncheckedIOException;

import mnm.hdfontgen.pack.PackGenerator;
import mnm.hdfontgen.pack.PackSettings;

public class FontGenerator {

    public static void generate(PackSettings settings, boolean parallel) throws UncheckedIOException, IOException {
        PackGenerator generator = settings.createGenerator();
        var pack = generator.generate();
        var filename = String.format("%s.zip", settings.description);
        Log.log("Rendering pages");
        pack.writeTo(filename, parallel);
        Log.log("Generated font at %s", filename);
    }
}