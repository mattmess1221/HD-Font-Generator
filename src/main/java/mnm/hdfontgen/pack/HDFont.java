package mnm.hdfontgen.pack;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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

    public record RenderResult(BufferedImage image, byte[] sizes) {
    }

    public RenderResult render(String[] chars) {
        var sizes = new ByteArrayOutputStream();
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
                var font = this.getFontForChar(charToRender);
                var wdth = getCharBounds(font, charToRender).getWidth();

                // Draw transparent background to persist monospace font width information.
                //
                // "The texture files of the default fonts contain a grid of white characters, which are automatically
                // colored by Minecraft as needed in-game. The character sizes are automatically determined based on the
                // last line of pixels containing any alpha value. Due to the way fonts are detected, filling the
                // background of a character with a color containing a 5% alpha background causes the full width to
                // render without generally having a visible background to the character. The default font character is
                // 8×8 pixels, while accented.png is 9×12 pixels."
                graphics.setClip(xPos, yPos, size, size);
                graphics.setColor(new Color(255, 255, 255, 13)); // 5% of 255 is 12.75, rounded to 13
                graphics.fillRect(xPos, yPos, (int) wdth, size);

                // Draw the foreground character.
                graphics.setClip(xPos, yPos, size, size);
                graphics.setColor(Color.WHITE);
                graphics.setFont(font);
                graphics.drawChars(new char[]{charToRender}, 0, 1, xPos, yPos + yOffset);

                var data = (int) Math.ceil(wdth / size * 0xf);
                sizes.write(data);
            }
        }
        graphics.dispose();
        return new RenderResult(image, sizes.toByteArray());
    }

    private Font getFontForChar(char ch) {
        final int size = this.size.getTextureSize() / 2;
        var f = (font.canDisplay(ch) ? font : fallbackFont).deriveFont(Font.PLAIN, size);
        f = f.deriveFont(Font.PLAIN, size);

        // decrease font size for large fonts.
        int s = size;

        Rectangle2D bounds;
        while ((bounds = getCharBounds(f, ch)) != null && (bounds.getHeight() > size || bounds.getWidth() > size)) {
            f = f.deriveFont(Font.PLAIN, --s);
        }
        return f;
    }

    private static Rectangle2D getCharBounds(Font f, char c) {
        var context = new FontRenderContext(f.getTransform(), false, false);
        return f.getStringBounds(new char[]{c}, 0, 1, context);
    }

    private static Font loadDefaultFont() {
        try (var in = HDFont.class.getResourceAsStream("/unifont-7.0.06.ttf")) {
            return Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(in));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException("Unable to read Unifont fallback font", e);
        }
    }
}
