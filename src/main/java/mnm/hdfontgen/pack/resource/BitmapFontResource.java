package mnm.hdfontgen.pack.resource;

import java.awt.image.BufferedImage;

import mnm.hdfontgen.pack.HDFont;
import mnm.hdfontgen.pack.ResourcePath;

public class BitmapFontResource extends AbstractBitmapResource {

    private final ResourcePath path;
    private final HDFont font;
    private final String[] characters;
    private byte[] sizes;

    public BitmapFontResource(ResourcePath path, HDFont font, String[] characters) {
        this.path = path;
        this.font = font;
        this.characters = characters;
    }

    @Override
    public ResourcePath getPath() {
        return this.path;
    }

    @Override
    protected BufferedImage render() {
        var rendered = font.render(characters);
        sizes = rendered.sizes();
        return rendered.image();
    }

    public byte[] getGlyphSizes() {
        return checkNotNull(sizes, "Tried to get glyph sizes for %s before it was rendered".formatted(this.path));
    }

    private static <T> T checkNotNull(T obj, String message) {
        if (obj == null) {
            throw new IllegalStateException(message);
        }
        return obj;
    }
}
