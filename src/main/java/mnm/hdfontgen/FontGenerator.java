package mnm.hdfontgen;

import mnm.hdfontgen.pack.GeneratorSettings;
import mnm.hdfontgen.pack.PackFormat;

import java.awt.*;
import java.io.IOException;

public class FontGenerator implements Runnable {

    private static boolean quiet;

    @Override
    public void run() {
        try {
            var window = GeneratorWindow.instance = new GeneratorWindow();
            window.frmHdFontGenerator.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generate(GeneratorSettings settings) throws IOException {
        var desc = settings.getDescription();
        var generator = settings.format.getFactory().create();
        var pack = generator.generate(settings);
        var filename = String.format("%s.zip", desc);
        Log.log("Rendering pages");
        pack.writeTo(filename);
        Log.log("Generated font at %s", filename);
    }

    public static void main(String[] args) {
        Log.addLogger(msg -> {
            if (!quiet) {
                System.out.println(msg);
            }
        });
        if (args.length == 0) {
            // open the gui
            EventQueue.invokeLater(new FontGenerator());
        } else {
            try {
                runCli(args);
            } catch (SystemExit e) {
                e.exit();
            }
        }
    }

    private static void runCli(String[] args) throws SystemExit {
        if (args.length >= 2 && args.length <= 4) {
            // headless
            var font = Font.decode(args[0]);
            var settings = new GeneratorSettings(font);
            var size = args[1];
            settings.size = TextureSize.forSize(Integer.parseInt(size))
                    .orElseThrow(() -> printSizes(size));
            if (args.length >= 3) {
                // flags, use any string to bypass
                settings.unicode = args[2].contains("u");
                quiet = args[2].contains("q");
            }
            if (args.length == 4) {
                // pack format
                var formatNumber = Integer.parseInt(args[3]);
                settings.format = PackFormat.values()[formatNumber];
            }

            try {
                FontGenerator.generate(settings);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(3);
            }
        } else {
            // wrong usage
            throw printUsage();
        }
    }

    private static SystemExit printSizes(String size) {
        return new SystemExit(2,
                size + " is not a supported texture size.",
                "Supported values are: " + TextureSize.getSizes()
        );
    }

    private static SystemExit printUsage() {
        return new SystemExit(1,
                "Command Line Usage: <font name> <texture size> [flags]",
                "Flags:",
                "    u    Export unicode",
                "    q    Quiet"
        );
    }

    private static class SystemExit extends Exception {
        private final int code;

        SystemExit(int code, String message) {
            super(message);
            this.code = code;
        }

        SystemExit(int code, String... message) {
            this(code, String.join("\n", message));
        }

        void exit() {
            System.out.println(this.getMessage());
            System.exit(this.code);
        }
    }
}