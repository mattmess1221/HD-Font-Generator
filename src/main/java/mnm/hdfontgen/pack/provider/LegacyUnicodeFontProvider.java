package mnm.hdfontgen.pack.provider;

import mnm.hdfontgen.pack.HDFont;
import mnm.hdfontgen.pack.resource.BitmapFontResource;
import mnm.hdfontgen.pack.GeneratorSettings;
import mnm.hdfontgen.pack.resource.Resource;
import mnm.hdfontgen.pack.ResourcePath;

class LegacyUnicodeFontProvider extends FontProvider {

    final ResourcePath sizes;
    final ResourcePath template;

    LegacyUnicodeFontProvider(ResourcePath sizes, ResourcePath template) {
        super("legacy_unicode");
        this.sizes = sizes;
        this.template = template;
    }

    private Resource[] createUnicodePages(HDFont font) {
        var resources = new Resource[0x100];
        var texturePath = String.format("textures/%s", template.getPath());
        texturePath = String.format(texturePath, "%02x");
        for (int pageIndex = 0; pageIndex < resources.length; pageIndex++) {
            String[] charTable = getUnicodeTable(pageIndex);
            var path = new ResourcePath(String.format(texturePath, pageIndex));
            resources[pageIndex] = new BitmapFontResource(path, font, charTable);
        }
        return resources;
    }

    private static String[] getUnicodeTable(int tableIndex) {
        int a = tableIndex << 8;
        var table = new String[16];
        for (int y = 0; y < 16; y++) {
            char[] line = new char[16];
            int b = y << 4;
            for (int x = 0; x < 16; x++) {
                line[x] = (char) (a + b + x);
            }
            table[y] = new String(line);
        }
        return table;
    }

    @Override
    public Resource[] getResources(GeneratorSettings settings) {
        // TODO save sizes bin file
        return this.createUnicodePages(settings.getFont());
    }
}
