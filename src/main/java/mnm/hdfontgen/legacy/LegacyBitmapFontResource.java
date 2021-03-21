package mnm.hdfontgen.legacy;

import mnm.hdfontgen.HDFont;

import java.awt.image.BufferedImage;

public class LegacyBitmapFontResource extends LegacyBitmapResource {

    private final String name;
    private final HDFont font;
    private final char[][] characters;

    public LegacyBitmapFontResource(String name, HDFont font, char[][] characters) {
        this.name = name;
        this.font = font;
        this.characters = characters;
    }

    @Override
    public String getPath() {
        return String.format("assets/minecraft/textures/font/%s.png", name);
    }

    @Override
    protected BufferedImage render() {
        return font.render(characters);
    }
}
