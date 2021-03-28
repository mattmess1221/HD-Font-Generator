package mnm.hdfontgen;

import mnm.hdfontgen.pack.PackFormat;
import mnm.hdfontgen.pack.PackGenerator;
import mnm.hdfontgen.pack.PackSettings;
import mnm.hdfontgen.pack.TextureSize;

import java.awt.*;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public static void generate(PackSettings.Bitmap settings, boolean parallel) throws UncheckedIOException, IOException {
        PackGenerator generator = settings.createGenerator();
        var pack = generator.generate();
        var filename = String.format("%s.zip", settings.description);
        Log.log("Rendering pages");
        pack.writeTo(filename, parallel);
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
        try {
            var options = parseOptions(args);
            if (options.containsKey("help")) {
                throw printUsage();
            }
            if (options.containsKey("quiet")) {
                quiet = true;
            }
            var format = parsePackFormat(options.getOrDefault("format", PackFormat.LATEST.name()));
            var builder = new PackSettings.Builder(format);
            if (options.containsKey("description")) {
                builder.description(options.get("description"));
            }
            var bitmap = builder.bitmap();
            bitmap.withFont(Font.decode(options.getOrDefault("font", "Dialog.plain")));
            bitmap.withSize(parseTextureSize(options.getOrDefault("size", TextureSize.x32.name())));
            bitmap.withUnicode(options.containsKey("unicode"));

            var settings = bitmap.build();
            FontGenerator.generate(settings, options.containsKey("parallel"));
        } catch (IOException | UncheckedIOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Parses long options and returns them as a map.
     */
    private static Map<String, String> parseOptions(String[] args) {
        var map = new HashMap<String, String>();
        for (var i = 0; i < args.length; i++) {
            var key = args[i];
            if (!key.startsWith("--")) {
                throw new IllegalArgumentException("unexpected non-option argument " + key);
            }
            key = key.substring(2);
            String value;
            if (key.contains("=")) {
                int index = key.indexOf("=");
                value = key.substring(index + 1);
                key = key.substring(0, index);
            } else {
                if (i + 1 == args.length || args[i + 1].startsWith("--")) {
                    value = "";
                } else {
                    value = args[i + 1];
                }
                i++;
            }
            map.put(key, value);
        }
        return map;
    }

    private static PackFormat parsePackFormat(String format) throws SystemExit {
        return parseEnum(PackFormat.class, format, PackFormat::getFormat)
                .orElseThrow(() -> printFormats(format));
    }

    private static TextureSize parseTextureSize(String size) throws SystemExit {
        return parseEnum(TextureSize.class, size, TextureSize::getTextureSize)
                .orElseThrow(() -> printSizes(size));
    }

    private static <T extends Enum<T>> Optional<T> parseEnum(Class<T> enumClass, String value, Function<T, Object> func) {
        for (var en : enumClass.getEnumConstants()) {
            if (Objects.toString(func.apply(en)).equals(value) || en.name().equals(value)) {
                return Optional.of(en);
            }
        }
        return Optional.empty();
    }

    private static SystemExit printFormats(String format) {
        var supported = Arrays.stream(PackFormat.values())
                .map(PackFormat::name)
                .collect(Collectors.joining(", "));

        return new SystemExit(2,
                format + " is not a supported pack format. Is it a newer version?",
                "Supported formats are: " + supported
        );
    }

    private static SystemExit printSizes(String size) {
        var supported = Arrays.stream(TextureSize.values())
                .map(TextureSize::getTextureSize)
                .collect(Collectors.toList());
        return new SystemExit(2,
                size + " is not a supported texture size.",
                "Supported values are: " + supported
        );
    }

    private static SystemExit printUsage() {
        return new SystemExit(1,
                "Command Line Usage: <font name> <texture size> [flags]",
                "Flags:",
                "    u    Export unicode",
                "    p    Enable parallel bitmap generation",
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