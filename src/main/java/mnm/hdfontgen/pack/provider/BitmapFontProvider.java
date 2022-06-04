package mnm.hdfontgen.pack.provider;

import mnm.hdfontgen.pack.FontPack;
import mnm.hdfontgen.pack.HDFont;
import mnm.hdfontgen.pack.ResourcePath;
import mnm.hdfontgen.pack.resource.BitmapFontResource;

class BitmapFontProvider extends FontProvider {

    transient final HDFont font;
    final ResourcePath file;
    final Integer height;
    final int ascent;
    final String[] chars;

    BitmapFontProvider(HDFont font, ResourcePath file, Integer height, int ascent, String[] chars) {
        super("bitmap");
        this.font = font;
        this.file = file;
        this.height = height;
        this.ascent = ascent;
        this.chars = chars;
    }

    BitmapFontProvider(HDFont font, ResourcePath file, int ascent, String[] chars) {
        this(font, file, null, ascent, chars);
    }

    @Override
    public void setup(FontPack pack) {
        String texturePath = String.format("textures/%s", file.path());
        ResourcePath path = new ResourcePath(file.namespace(), texturePath);

        pack.addResource(new BitmapFontResource(path, font, chars));
    }
}
