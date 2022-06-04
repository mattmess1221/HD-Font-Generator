package mnm.hdfontgen.pack;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mnm.hdfontgen.IOUtils;
import mnm.hdfontgen.Log;
import mnm.hdfontgen.pack.resource.Resource;

public class FontPack {

    private final List<Resource> resources = new ArrayList<>();

    public void addResource(Resource resource) {
        this.resources.add(resource);
    }

    public void addResources(Collection<? extends Resource> resources) {
        this.resources.addAll(resources);
    }

    public void writeTo(FileSystem zipFs, boolean parallel) throws UncheckedIOException {
        // write all the pages
        this.resources.stream()
                .collect(Collectors.groupingBy(Resource::order))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .forEach(resources -> {

                    var stream = resources.stream();
                    if (parallel) {
                        stream = stream.parallel();
                    }
                    stream.forEach(IOUtils.consume(page -> {
                        Path pagePath = zipFs.getPath(page.getPath().getFileLocation());
                        Log.log("Writing %s", pagePath.getFileName());
                        if (pagePath.getParent() != null) {
                            Files.createDirectories(pagePath.getParent());
                        }
                        page.writeTo(pagePath);
                    }));
                });
    }

    public void writeTo(String filename, boolean parallel) throws IOException, UncheckedIOException {
        var file = Paths.get(filename);
        if (Files.exists(file)) {
            Files.delete(file);
        }
        var uri = URI.create("jar:" + file.toUri());
        var env = new HashMap<String, String>();
        env.put("create", "true");

        try (var fs = FileSystems.newFileSystem(uri, env)) {
            this.writeTo(fs, parallel);
        }
    }

}
