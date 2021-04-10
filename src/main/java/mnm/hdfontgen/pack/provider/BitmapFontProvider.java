package mnm.hdfontgen.pack.provider;

import mnm.hdfontgen.pack.HDFont;
import mnm.hdfontgen.pack.ResourcePath;
import mnm.hdfontgen.pack.resource.BitmapFontResource;
import mnm.hdfontgen.pack.resource.Resource;

class BitmapFontProvider extends FontProvider {

    private transient final HDFont font;
    private final ResourcePath file;
    private final Integer height;
    private final int ascent;
    private final String[] chars;

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
    public Resource[] getResources() {
        String texturePath = String.format("textures/%s", file.getPath());
        ResourcePath path = new ResourcePath(file.getNamespace(), texturePath);

        return new Resource[]{
                new BitmapFontResource(path, font, chars)
        };
    }
}
