package mnm.hdfontgen;

import java.awt.Font;

public class HDFont {

    private final Font font;
    private TextureSize size;
    private boolean unicode;

    public HDFont(Font font) {
        this(font, TextureSize.x32, false);
    }

    public HDFont(Font font, TextureSize size, boolean unicode) {
        this.font = font;
        this.size = size;
        this.unicode = unicode;
    }

    public Font getFont() {
        return font;
    }

    public TextureSize getSize() {
        return size;
    }

    public boolean isUnicode() {
        return unicode;
    }

    @Override
    public String toString() {
        return font.getFontName();
    }

    public String getFriendlyName() {
        return toString() + " " + getSize() + (isUnicode() ? " with unicode" : "");
    }
}
