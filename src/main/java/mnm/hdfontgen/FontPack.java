package mnm.hdfontgen;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FontPack {

    private static final char[][] ascii = loadAsciiTxt();
    private static final String packJson = loadPackJson();

    private final String description;
    private final List<FontPage> pages = new ArrayList<>();

    public FontPack(String description) {
        this.description = description;
    }

    private void addPage(FontPage page) {
        this.pages.add(page);
    }

    public void addAsciiPage(HDFont font) {
        this.addPage(new FontPage("ascii", font, ascii));
    }

    public void addUnicodePages(HDFont font) {
        for (int pageIndex = 0x00; pageIndex <= 0xff; pageIndex++) {
            char[][] charTable = getUnicodeTable(pageIndex);
            String name = String.format("unicode_page_%02x", pageIndex);
            this.addPage(new FontPage(name, font, charTable));
        }
    }

    public void writeTo(ZipOutputStream zipOut) throws IOException {

        // write the pack.mcmeta
        zipOut.putNextEntry(new ZipEntry("pack.mcmeta"));
        String packJson = String.format(FontPack.packJson, description);
        zipOut.write(packJson.getBytes(StandardCharsets.UTF_8));
        zipOut.closeEntry();
        zipOut.flush();

        // write al the pages
        for (FontPage font : pages) {
            zipOut.putNextEntry(new ZipEntry(font.getPath()));
            font.writeToStream(zipOut);
            zipOut.closeEntry();
        }

        // flush the stream so it writes properly
        zipOut.finish();
    }

    public void writeTo(String filename) throws IOException {
        try (ZipOutputStream zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))) {
            this.writeTo(zipOut);
        }
    }

    private static char[][] getUnicodeTable(int tableIndex) {
        int a = tableIndex << 8;
        char[][] table = new char[16][16];
        for (int y = 0; y < 16; y++) {
            int b = y << 4;
            for (int x = 0; x < 16; x++) {
                table[y][x] = (char) (a + b + x);
            }
        }
        return table;
    }

    private static char[][] loadAsciiTxt() {
        try {
            return readInputStream("/ascii.txt")
                    .lines()
                    .map(String::toCharArray)
                    .toArray(char[][]::new);
        } catch (Exception e) {
            throw new RuntimeException("Ascii file is invalid.", e);
        }
    }

    private static String loadPackJson() {
        // TODO generate with json-simple
        try {
            return readInputStream("/pack.mcmeta.json")
                    .lines()
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Unable to read pack.mcmeta template", e);
        }
    }

    private static BufferedReader readInputStream(String name) throws IOException {
        InputStream inputStream = FontPack.class.getResourceAsStream(name);
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }
}
