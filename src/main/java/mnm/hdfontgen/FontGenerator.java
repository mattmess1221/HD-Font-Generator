package mnm.hdfontgen;

import java.awt.*;
import java.io.IOException;

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
    public static String generate(HDFont font, boolean unicode) throws IOException {
        String description = font.getFriendlyName(unicode);
        FontPack pack = new FontPack(description);
        pack.addAsciiPage(font);
        if (unicode) {
            pack.addUnicodePages(font);
        }

        Log.log("Rendering pages");

        String filename = description + ".zip";

        pack.writeTo(filename);

        printSuccess(filename);
        return filename;
    }

    public static void main(String[] args) throws IOException {
        Log.addLogger(msg -> {
            if (!quiet) {
                System.out.println(msg);
            }
        });
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

            FontGenerator.generate(new HDFont(Font.decode(name), texSize), unicode);
        } else {
            // wrong usage
            printUsage();
            System.exit(1);
        }
    }

    private static void printSuccess(String filename) {
        Log.log("Generated font at %s", filename);
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