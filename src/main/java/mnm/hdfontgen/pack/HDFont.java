package mnm.hdfontgen.pack;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class HDFont {

    private static final Font fallbackFont = loadDefaultFont();

    private final Font font;
    private final TextureSize size;

    public HDFont(Font font, TextureSize size) {
        this.font = font;
        this.size = size;
    }

    public BufferedImage render(String[] chars) {
        int size = this.size.getTextureSize() / 2;
        int yOffset = size - size / 4;
        var image = new BufferedImage(size * chars[0].length(), size * chars.length, BufferedImage.TYPE_INT_ARGB);
        var graphics = (Graphics2D) image.getGraphics();
        for (int y = 0; y < chars.length; y++) {
            for (int x = 0; x < chars[y].length(); x++) {
                char charToRender = chars[y].charAt(x);
                if (charToRender == '\0' && x != 0 && y != 0) {
                    continue;
                }
                int xPos = x * size;
                int yPos = y * size;
                graphics.setClip(xPos, yPos, size, size);
                graphics.setFont(this.getFontForChar(charToRender));
                graphics.drawChars(new char[]{charToRender}, 0, 1, xPos, yPos + yOffset);
            }
        }
        graphics.dispose();
        return image;
    }

    private Font getFontForChar(char ch) {
        final int size = this.size.getTextureSize() / 2;
        var f = (font.canDisplay(ch) ? font : fallbackFont).deriveFont(Font.PLAIN, size);
        f = f.deriveFont(Font.PLAIN, size);

        String stringToCheck = Character.toString(ch);

        // decrease font size for large fonts.
        int s = size;
        while (getStringHeight(f, stringToCheck) > size) {
            f = f.deriveFont(Font.PLAIN, s--);
        }
        return f;
    }

    private static double getStringHeight(Font f, String string) {
        var context = new FontRenderContext(f.getTransform(), false, false);
        return f.getStringBounds(string, context).getHeight();
    }

    private static Font loadDefaultFont() {
        try (var in = HDFont.class.getResourceAsStream("/unifont-7.0.06.ttf")) {
            return Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(in));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException("Unable to read Unifont fallback font", e);
        }
    }
}
