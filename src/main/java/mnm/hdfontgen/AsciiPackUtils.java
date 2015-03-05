package mnm.hdfontgen;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

public class AsciiPackUtils {

    private static char[][] ascii;
    private static String packJson;

    /**
     * Renders a ascii {@link BufferedImage} of the given font.
     * 
     * @param font The HD font
     * @return The rendered font
     * @throws IOException
     */
    public static BufferedImage render(HDFont font) throws IOException {
        loadAsciiTxt();

        return render(font, ascii);

    }

    public static BufferedImage render(HDFont font, int tableInt) {
        int a = tableInt << 8;
        char[][] table = new char[16][16];
        for (int y = 0; y < 16; y++) {
            int b = y << 4;
            for (int x = 0; x < 16; x++) {
                table[y][x] = (char) (a + b + x);
            }
        }
        return render(font, table);
    }

    /**
     * Renders the character array of the given font onto a
     * {@link BufferedImage}
     * 
     * @param font
     * @param ascii
     * @return
     */
    public static BufferedImage render(HDFont font, char[][] ascii) {
        final int size = font.getSize().getTextureSize() / 2;
        BufferedImage image = new BufferedImage(size * 16, size * 16, BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics2D g2d = image.createGraphics();

        for (int y = 0; y < 16; y++) { // rows
            for (int x = 0; x < 16; x++) { // columns
                char ch = ascii[y][x];
                int yy = size - size / 4; // move each character up a bit

                // Limit the render of each character so they don't spill.
                BufferedImage c = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB_PRE);
                Graphics2D gc = c.createGraphics();

                // pre-render the character
                gc.setFont(new Font(font.getFont().getName(), 0, size));
                gc.drawChars(new char[] { ch }, 0, 1, 0, yy);
                gc.dispose();

                // draw the pre-rendered character
                g2d.drawImage(c, x * size, y * size, null);
            }
        }
        g2d.dispose();
        return image;
    }

    /**
     * Creates a resource pack containing the image as the ascii font.
     * 
     * @param description The description and name of the resource pack
     * @param ascii The ASCII font image to use.
     */
    public static void pack(String description, List<FontTexture> list) throws IOException {
        loadPackJson();

        File zip = new File(description + ".zip");
        FileOutputStream fout = null;
        ZipOutputStream zout = null;
        try {
            // prepare the streams
            fout = new FileOutputStream(zip);
            zout = new ZipOutputStream(fout);

            // write the pack.mcmeta
            zout.putNextEntry(new ZipEntry("pack.mcmeta"));
            byte[] mcmeta = String.format(packJson, description).getBytes();
            zout.write(mcmeta, 0, mcmeta.length);
            zout.closeEntry();

            // write the ascii.png
            for (FontTexture font : list) {
                zout.putNextEntry(new ZipEntry(font.getPath()));
                ImageIO.write(font.getImage(), "png", zout);
                zout.closeEntry();
            }

            // cross the streams
            zout.finish();
            zout.flush();
            fout.flush();
        } finally {
            closeQuietly(fout);
            closeQuietly(zout);
        }
    }

    private static void loadAsciiTxt() throws IOException {
        if (ascii == null) {
            try {
                String[] split = readInputStream("ascii.txt").split("\n");
                ascii = new char[16][16];
                for (int i = 0; i < split.length; i++) {
                    ascii[i] = split[i].toCharArray();
                }
            } catch (Exception e) {
                throw new IOException("Ascii file is invalid.", e);
            }
        }
    }

    private static void loadPackJson() throws IOException {
        if (packJson == null) {
            packJson = readInputStream("pack.mcmeta.json");
        }
    }

    private static String readInputStream(String name) throws IOException {
        InputStream in = ClassLoader.getSystemResourceAsStream(name);
        if (in == null) {
            throw new IOException(name + " does not exist.");
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            while (in.available() > 0) {
                baos.write(in.read());
            }
            return baos.toString("utf-8");
        } finally {
            closeQuietly(in);
            closeQuietly(baos);
        }
    }

    private static void closeQuietly(Closeable close) {
        if (close != null) {
            try {
                close.close();
            } catch (IOException e) {
                // be quiet
            }
        }
    }
}
