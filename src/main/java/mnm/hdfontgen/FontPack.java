package mnm.hdfontgen;

import com.google.gson.Gson;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void writeTo(FileSystem zipFs) throws IOException {
        // write the pack.mcmeta
        Log.log("Writing pack.mcmeta");
        try (Writer writer = Files.newBufferedWriter(zipFs.getPath("pack.mcmeta"))) {
            PackSection section = new PackSection(this.packFormat, this.description);
            PackJson packJson = new PackJson(section);
            new Gson().toJson(packJson, writer);
        }

        // write al the pages
        for (FontPage page : pages) {
            Log.log("Writing %s", page.getName());
            Path pagePath = zipFs.getPath(page.getPath());
            Files.createDirectories(pagePath.getParent());
            try (OutputStream out = Files.newOutputStream(zipFs.getPath(page.getPath()))) {
                ImageIO.write(page.render(), "png", out);
            }
        }
    }

    public void writeTo(String filename) throws IOException {
        Path file = Paths.get(filename);
        URI uri = URI.create("jar:" + file.toUri());
        Map<String, String> env = new HashMap<>();
        env.put("create", "true");

        try (FileSystem fs = FileSystems.newFileSystem(uri, env)) {
            this.writeTo(fs);
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
        try (BufferedReader reader = readResource("/ascii.txt")) {
            return reader.lines()
                    .map(String::toCharArray)
                    .toArray(char[][]::new);
        } catch (Exception e) {
            throw new RuntimeException("Resource 'ascii.txt' could not be read", e);
        }
    }

    private static BufferedReader readResource(String name) {
        InputStream inputStream = FontPack.class.getResourceAsStream(name);
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }
}
