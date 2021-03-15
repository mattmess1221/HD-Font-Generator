package mnm.hdfontgen;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.OutputStream;

public class FontPage {

    private final String name;
    private final HDFont font;
    private final char[][] characters;

    public FontPage(String name, HDFont font, char[][] characters) {
        this.name = name;
        this.font = font;
        this.characters = characters;
    }

    public String getPath() {
        return String.format("assets/minecraft/textures/font/%s.png", name);
    }

    public void writeToStream(OutputStream zip) throws IOException {
        System.out.println("Rendering page " + name);
        ImageIO.write(font.render(characters), "png", zip);
    }
}
