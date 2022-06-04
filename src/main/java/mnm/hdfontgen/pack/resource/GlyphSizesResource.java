package mnm.hdfontgen.pack.resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import mnm.hdfontgen.IOUtils;
import mnm.hdfontgen.pack.ResourcePath;
import mnm.hdfontgen.pack.ZipPath;

public class GlyphSizesResource implements Resource {

    private final ResourcePath path;
    private final List<BitmapFontResource> fontTextures;

    public GlyphSizesResource(ResourcePath path, List<BitmapFontResource> fontTextures) {
        this.path = path;
        this.fontTextures = fontTextures;
    }

    @Override
    public ZipPath getPath() {
        return path;
    }

    @Override
    public void writeTo(Path path) throws IOException {
        try (var o = Files.newOutputStream(path)) {
            fontTextures.stream()
                    .map(BitmapFontResource::getGlyphSizes)
                    .forEach(IOUtils.consume(o::write));
        }
    }

    @Override
    public int order() {
        return 1;
    }
}
