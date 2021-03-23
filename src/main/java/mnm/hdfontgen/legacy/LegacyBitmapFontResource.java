package mnm.hdfontgen.legacy;

import mnm.hdfontgen.HDFont;
import mnm.hdfontgen.pack.AbstractBitmapResource;
import mnm.hdfontgen.pack.ResourcePath;

import java.awt.image.BufferedImage;

public class LegacyBitmapFontResource extends AbstractBitmapResource {

    private final ResourcePath path;
    private final HDFont font;
    private final char[][] characters;

    public LegacyBitmapFontResource(ResourcePath path, HDFont font, char[][] characters) {
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
        return font.render(characters);
    }
}
