package mnm.hdfontgen.pack.provider;

import mnm.hdfontgen.pack.resource.BitmapFontResource;
import mnm.hdfontgen.pack.GeneratorSettings;
import mnm.hdfontgen.pack.resource.Resource;
import mnm.hdfontgen.pack.ResourcePath;

class BitmapFontProvider extends FontProvider {

    private final ResourcePath file;
    private final Integer height;
    private final int ascent;
    private final String[] chars;

    BitmapFontProvider(ResourcePath file, Integer height, int ascent, String[] chars) {
        super("bitmap");
        this.file = file;
        this.height = height;
        this.ascent = ascent;
        this.chars = chars;
    }

    BitmapFontProvider(ResourcePath file, int ascent, String[] chars) {
        this(file, null, ascent, chars);
    }

    @Override
    public Resource[] getResources(GeneratorSettings settings) {
        var font = settings.getFont();

        String texturePath = String.format("textures/%s", file.getPath());
        ResourcePath path = new ResourcePath(file.getNamespace(), texturePath);

        return new Resource[]{
                new BitmapFontResource(path, font, chars)
        };
    }
}
