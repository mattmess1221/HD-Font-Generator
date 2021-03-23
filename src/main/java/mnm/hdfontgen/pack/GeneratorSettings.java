package mnm.hdfontgen.pack;

import mnm.hdfontgen.TextureSize;

import java.awt.*;

public class GeneratorSettings {
    public final Font font;
    public TextureSize size = TextureSize.x32;
    public PackFormat format = PackFormat.LATEST;

    public String description;
    public boolean unicode = false;

    public GeneratorSettings(Font font) {
        this.font = font;

    }

    public String getDescription() {
        if (description == null) {
            var fontName = font.getFontName();
            var withUnicode = unicode ? " with unicode" : "";
            var versions = format.getVersionRange();

            description = String.format("%s %s%s for Minecraft %s", fontName, size, withUnicode, versions);
        }
        return description;
    }
}
