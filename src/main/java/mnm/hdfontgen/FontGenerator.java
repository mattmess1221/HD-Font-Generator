package mnm.hdfontgen;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FontGenerator implements Runnable {

    private static boolean quiet;

    @Override
    public void run() {
        try {
            GeneratorWindow window = GeneratorWindow.instance = new GeneratorWindow();
            window.frmHdFontGenerator.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generate(HDFont font) throws IOException {
        List<FontTexture> list = new ArrayList<>();

        String desc = font.getFriendlyName();

        if (!quiet)
            System.out.println("Rendering ascii");

        BufferedImage ascii = AsciiPackUtils.render(font);
        list.add(new FontTexture("ascii", ascii));
        if (font.isUnicode()) {
            for (int i = 0x00; i <= 0xff; i++) {
                String name = Integer.toString(i, 16);
                if (i < 0x10) {
                    name = 0 + name;
                }

                if (!quiet)
                    System.out.println("Rendering unicode page " + name);

                BufferedImage page = AsciiPackUtils.render(font, i);
                list.add(new FontTexture("unicode_page_" + name, page));
            }
        }
        if (!quiet)
            System.out.println("Preparing resource pack.");

        AsciiPackUtils.pack(desc, list);
        printSuccess(font);
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            // open the gui
            EventQueue.invokeLater(new FontGenerator());
        } else if (args.length == 2 || args.length == 3) {
            // headless
            String name = args[0].replace('_', ' ');
            String size = args[1];
            boolean unicode = false;
            if (args.length == 3) {
                // flags
                unicode = args[2].contains("u");
                quiet = args[2].contains("q");
            }

            TextureSize texSize = TextureSize.forSize(Integer.parseInt(size));
            if (texSize == null) {
                printSizes(size);
                System.exit(1);
            }

            FontGenerator.generate(new HDFont(Font.decode(name), texSize, unicode));
        } else {
            // wrong usage
            printUsage();
            System.exit(1);
        }
    }

    private static void printSuccess(HDFont hdfont) {
        if (!quiet)
            System.out.println("Generated font: " + hdfont.getFriendlyName() + ".zip");
    }

    private static void printSizes(String size) {
        System.out.println(size + " is not a supported texture size.\n" + " Supported values are: "
                + TextureSize.getSizes());
    }

    private static void printUsage() {
        System.out.println("Command Line Usage: <font name> <texture size> [flags]");
        System.out.println("Flags:");
        System.out.println("    u    Export unicode");
        System.out.println("    q    Quiet");
    }
}