package mnm.hdfontgen.legacy;

import mnm.hdfontgen.pack.FontPack;
import mnm.hdfontgen.HDFont;
import mnm.hdfontgen.pack.GeneratorSettings;
import mnm.hdfontgen.pack.PackGenerator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class LegacyFontGenerator implements PackGenerator {

    private static final char[][] ascii = loadAsciiTxt();
    private final int packFormat;

    public LegacyFontGenerator(int packFormat) {
        this.packFormat = packFormat;
    }

    private static void addAsciiPage(FontPack pack, HDFont font) {
        pack.addResource(new LegacyBitmapFontResource("ascii", font, ascii));
    }

    private void addUnicodePages(FontPack pack, HDFont font) {
        for (int pageIndex = 0x00; pageIndex <= 0xff; pageIndex++) {
            char[][] charTable = getUnicodeTable(pageIndex);
            String name = String.format("unicode_page_%02x", pageIndex);
            pack.addResource(new LegacyBitmapFontResource(name, font, charTable));
        }
    }

    @Override
    public FontPack generate(GeneratorSettings settings) {
        String desc = settings.font.getFriendlyName(settings.unicode);
        FontPack pack = new FontPack(this.packFormat, desc);

        addAsciiPage(pack, settings.font);
        if (settings.unicode) {
            addUnicodePages(pack, settings.font);
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
