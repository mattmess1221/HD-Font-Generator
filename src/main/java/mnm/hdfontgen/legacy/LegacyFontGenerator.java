package mnm.hdfontgen.legacy;

import mnm.hdfontgen.pack.FontPack;
import mnm.hdfontgen.HDFont;
import mnm.hdfontgen.pack.GeneratorSettings;
import mnm.hdfontgen.pack.PackGenerator;
import mnm.hdfontgen.pack.PackJson;
import mnm.hdfontgen.pack.ResourcePath;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class LegacyFontGenerator implements PackGenerator {

    private static final char[][] ascii = loadAsciiTxt();

    public LegacyFontGenerator() {
    }

    private static LegacyBitmapFontResource createBitmap(String name, HDFont font, char[][] characters) {
        ResourcePath path = new ResourcePath(String.format("textures/font/%s.png", name));
        return new LegacyBitmapFontResource(path, font, characters);
    }

    private static void addAsciiPage(FontPack pack, HDFont font) {
        pack.addResource(createBitmap("ascii", font, ascii));
    }

    private void addUnicodePages(FontPack pack, HDFont font) {
        for (int pageIndex = 0x00; pageIndex <= 0xff; pageIndex++) {
            char[][] charTable = getUnicodeTable(pageIndex);
            String name = String.format("unicode_page_%02x", pageIndex);
            pack.addResource(createBitmap(name, font, charTable));
        }
    }

    @Override
    public FontPack generate(GeneratorSettings settings) {
        FontPack pack = new FontPack();
        pack.addResource(new PackJson(settings.format.getFormat(), settings.getDescription()));

        var hdfont = new HDFont(settings.font, settings.size);

        addAsciiPage(pack, hdfont);
        if (settings.unicode) {
            addUnicodePages(pack, hdfont);
        }

        return pack;
    }

    private static char[][] getUnicodeTable(int tableIndex) {
        int a = tableIndex << 8;
        var table = new char[16][16];
        for (int y = 0; y < 16; y++) {
            int b = y << 4;
            for (int x = 0; x < 16; x++) {
                table[y][x] = (char) (a + b + x);
            }
        }
        return table;
    }

    private static char[][] loadAsciiTxt() {
        try (var reader = readResource("/ascii.txt")) {
            return reader.lines()
                    .map(String::toCharArray)
                    .toArray(char[][]::new);
        } catch (Exception e) {
            throw new RuntimeException("Resource 'ascii.txt' could not be read", e);
        }
    }

    private static BufferedReader readResource(String name) {
        var inputStream = FontPack.class.getResourceAsStream(name);
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }
}
