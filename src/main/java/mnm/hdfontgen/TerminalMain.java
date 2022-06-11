package mnm.hdfontgen;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import mnm.hdfontgen.pack.PackFormat;
import mnm.hdfontgen.pack.PackSettings;
import mnm.hdfontgen.pack.TextureSize;
import mnm.hdfontgen.pack.provider.FontProvidersJson;

public class TerminalMain {

    private static boolean quiet;

    static {
        Log.addLogger(msg -> {
            if (!quiet) {
                System.out.println(msg);
            }
        });
    }

    public static void main(String[] args) throws SystemExit {
        try {
            var options = parseOptions(args);
            if (options.containsKey("help")) {
                throw printUsage();
            }
            if (options.containsKey("quiet")) {
                quiet = true;
            }

            var settings = parseSettings(options);

            FontGenerator.generate(settings, options.containsKey("parallel"));
        } catch (IOException | UncheckedIOException e) {
            throw new RuntimeException(e);
        } catch (SystemExit e) {
            e.exit();
        }
    }

    private static PackSettings parseSettings(Map<String, String> options) throws SystemExit {
        var format = parsePackFormat(requireOption(options, "format"));
        var builder = new PackSettings.Builder(format);
        if (options.containsKey("description")) {
            builder = builder.withDescription(options.get("description"));
        }
        var type = requireOption(options, "type");
        return (switch (type) {
            case "bitmap" -> builder.bitmap(FontProvidersJson.DEFAULT_NAME, b -> b
                    .withFont(requireOption(options, "font"))
                    .withSize(parseTextureSize(options.getOrDefault("size", TextureSize.x32.name())))
                    .withUnicode(options.containsKey("unicode"))
            );
            case "truetype" -> builder.trueType(FontProvidersJson.DEFAULT_NAME, b -> b
                    .withFont(Paths.get(requireOption(options, "font")))
                    .withOversample(Float.parseFloat(options.getOrDefault("oversample", "1")))
            );
            default -> throw printUnsupportedOption("type", type, "bitmap", "truetype");
        }).build();
//        return builder.build();
    }

    private static String requireOption(Map<String, String> options, String key) throws SystemExit {
        var value = options.get(key);
        if (value == null) {
            throw printMissingOption(key);
        }
        return value;
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

    private static SystemExit printMissingOption(String option) {
        return new SystemExit(2,
                "Option --" + option + " is missing and is required."
        );
    }

    private static SystemExit printUnsupportedOption(String option, String value, String... values) {
        var supported = String.join(", ", values);
        return new SystemExit(2,
                value + " is not a supported " + option + ".",
                "Supported values are: " + supported
        );
    }

    private static SystemExit printFormats(String format) {
        var supported = Arrays.stream(PackFormat.values())
                .map(PackFormat::getFormat)
                .map(Objects::toString)
                .toArray(String[]::new);
        return printUnsupportedOption("pack format", format, supported);
    }

    private static SystemExit printSizes(String size) {
        var supported = Arrays.stream(TextureSize.values())
                .map(TextureSize::getTextureSize)
                .map(Objects::toString)
                .toArray(String[]::new);
        return printUnsupportedOption("texture size", size, supported);
    }

    private static SystemExit printUsage() {
        return new SystemExit(0,
                "Command Line Usage",
                "Options:",
                "    --type <type>           The type of font to generate (bitmap, truetype)",
                "    --format <fmt>          The pack format to use",
                "    --description <desc>    The description to use for the pack",
                "    --quiet                 Disable log output",
                "    --help                  Displays this help text and exits",
                "Bitmap Options:",
                "    --font <name>           The name of the font registered in the system to use",
                "    --size <size>           The size of font to use " + Arrays.stream(TextureSize.values()).map(Object::toString).collect(Collectors.joining(", ", "(", ")")),
                "    --unicode               Generates unicode pages",
                "TrueType Options:",
                "    --font <path>           The file path to a ttf or otf file",
                "    --oversample <num>      A decimal number specifying the resolution multiplier of the font"
        );
    }

    private static class SystemExit extends RuntimeException {
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
