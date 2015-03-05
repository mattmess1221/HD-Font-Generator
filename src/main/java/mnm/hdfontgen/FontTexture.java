package mnm.hdfontgen;

import java.awt.image.BufferedImage;

public class FontTexture {

    private String name;
    private BufferedImage image;

    public FontTexture(String name, BufferedImage image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return String.format("assets/minecraft/textures/font/%s.png", name);
    }

    public BufferedImage getImage() {
        return image;
    }
}
