package mnm.hdfontgen;

import com.google.gson.Gson;

import javax.imageio.ImageIO;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FontPack {

    private static final char[][] ascii = loadAsciiTxt();

    private final int packFormat;
    private final String description;
    private final List<FontPage> pages = new ArrayList<>();

    public FontPack(int packFormat, String description) {
        this.packFormat = packFormat;
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
        Gson gson = new Gson();
        // write the pack.mcmeta
        Log.log("Writing pack.mcmeta");
        zipOut.putNextEntry(new ZipEntry("pack.mcmeta"));
        PackSection section = new PackSection(this.packFormat, this.description);
        PackJson packJson = new PackJson(section);
        zipOut.write(gson.toJson(packJson).getBytes(StandardCharsets.UTF_8));
        zipOut.closeEntry();

        // write al the pages
        for (FontPage page : pages) {
            Log.log("Writing %s", page.getName());
            zipOut.putNextEntry(new ZipEntry(page.getPath()));
            ImageIO.write(page.render(), "png", zipOut);
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

    private static BufferedReader readInputStream(String name) {
        InputStream inputStream = FontPack.class.getResourceAsStream(name);
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }
}
