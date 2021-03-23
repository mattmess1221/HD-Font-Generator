package mnm.hdfontgen.pack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class AbstractJsonResource implements Resource {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected Object getRoot() {
        return this;
    }

    @Override
    public void writeTo(Path path) throws IOException {
        try (var writer = Files.newBufferedWriter(path)) {
            gson.toJson(getRoot(), writer);
        }
    }
}
