package mnm.hdfontgen;

import java.awt.image.BufferedImage;

public class FontPage {

    private final String name;
    private final HDFont font;
    private final char[][] characters;

    public FontPage(String name, HDFont font, char[][] characters) {
        this.name = name;
        this.font = font;
        this.characters = characters;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return String.format("assets/minecraft/textures/font/%s.png", name);
    }

    public BufferedImage render() {
        return font.render(characters);
    }
}
