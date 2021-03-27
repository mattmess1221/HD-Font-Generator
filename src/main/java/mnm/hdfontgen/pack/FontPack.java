package mnm.hdfontgen.pack;

import mnm.hdfontgen.Log;
import mnm.hdfontgen.pack.resource.Resource;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class FontPack {

    private final List<Resource> resources = new ArrayList<>();

    public void addResource(Resource resource) {
        this.resources.add(resource);
    }

    public void addResources(Resource[] resources) {
        for (Resource res : resources) {
            this.addResource(res);
        }
    }

    public void writeTo(FileSystem zipFs, boolean parallel) throws UncheckedIOException {
        // write all the pages
        var stream = resources.stream();
        if (parallel) {
            stream = stream.parallel();
        }
        stream.forEach(consume(page -> {
            Path pagePath = zipFs.getPath(page.getPath().getFileLocation());
            Log.log("Writing %s", pagePath.getFileName());
            if (pagePath.getParent() != null) {
                Files.createDirectories(pagePath.getParent());
            }
            page.writeTo(pagePath);
        }));
    }

    public void writeTo(String filename, boolean parallel) throws IOException, UncheckedIOException {
        var file = Paths.get(filename);
        var uri = URI.create("jar:" + file.toUri());
        var env = new HashMap<String, String>();
        env.put("create", "true");

        try (var fs = FileSystems.newFileSystem(uri, env)) {
            this.writeTo(fs, parallel);
        }
    }

    private static <T> Consumer<T> consume(IOConsumer<T> consumer) {
        return obj -> {
            try {
                consumer.accept(obj);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        };
    }

    private interface IOConsumer<T> {
        void accept(T obj) throws IOException;

    }
}
