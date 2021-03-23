package mnm.hdfontgen.pack;

import mnm.hdfontgen.HDFont;

import java.awt.image.BufferedImage;

public class BitmapFontResource extends AbstractBitmapResource {

    private final ResourcePath path;
    private final HDFont font;
    private final String[] characters;

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
        return font.render(characters);
    }
}
