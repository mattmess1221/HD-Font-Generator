package mnm.hdfontgen.pack.provider;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import mnm.hdfontgen.pack.FontPack;
import mnm.hdfontgen.pack.HDFont;
import mnm.hdfontgen.pack.ResourcePath;
import mnm.hdfontgen.pack.resource.BitmapFontResource;
import mnm.hdfontgen.pack.resource.GlyphSizesResource;

class LegacyUnicodeFontProvider extends FontProvider {

    private transient final HDFont font;
    final ResourcePath sizes;
    final ResourcePath template;

    LegacyUnicodeFontProvider(HDFont font, ResourcePath sizes, ResourcePath template) {
        super("legacy_unicode");
        this.font = font;
        this.sizes = sizes;
        this.template = template;
    }

    private List<BitmapFontResource> createUnicodePages(HDFont font) {
        // file template uses hex integers, json template uses strings
        var texturePath = template.path().replace("%s", "%02x");
        return IntStream.range(0, 0x100).mapToObj(pageIndex -> {
            String[] charTable = getUnicodeTable(pageIndex);
            var path = new ResourcePath(template.namespace(), texturePath.formatted(pageIndex));
            return new BitmapFontResource(path, font, charTable);
        }).collect(Collectors.toList());
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
    public void setup(FontPack pack) {
        var pages = this.createUnicodePages(font);
        pack.addResources(pages);
        pack.addResource(new GlyphSizesResource(this.sizes, pages));
    }
}
