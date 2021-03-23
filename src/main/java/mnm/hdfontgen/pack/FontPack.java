package mnm.hdfontgen.pack;

import mnm.hdfontgen.Log;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FontPack {

    private final List<Resource> resources = new ArrayList<>();

    public void addResource(Resource page) {
        this.resources.add(page);
    }

    public void writeTo(FileSystem zipFs) throws IOException {
        // write all the pages
        for (Resource page : resources) {
            Path pagePath = zipFs.getPath(page.getPath());
            Log.log("Writing %s", pagePath.getFileName());
            if (pagePath.getParent() != null) {
                Files.createDirectories(pagePath.getParent());
            }
            page.writeTo(pagePath);
        }
    }

    public void writeTo(String filename) throws IOException {
        var file = Paths.get(filename);
        var uri = URI.create("jar:" + file.toUri());
        var env = new HashMap<String, String>();
        env.put("create", "true");

        try (var fs = FileSystems.newFileSystem(uri, env)) {
            this.writeTo(fs);
        }
    }

}
