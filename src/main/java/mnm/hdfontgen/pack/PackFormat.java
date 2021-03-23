package mnm.hdfontgen.pack;

import mnm.hdfontgen.legacy.LegacyFontGenerator;

public enum PackFormat {
    V1(1, "1.6.1", "1.8.9", LegacyFontGenerator::new),
    V2(2, "1.9", "1.10.2", LegacyFontGenerator::new),
    V3(3, "1.11", "1.12.2", LegacyFontGenerator::new),
//    V4(4, "1.13", "1.14.4", null),
//    V5(5, "1.15", "1.16.1", null),
//    V6(6, "1.16.2", "1.16.5", null),
//    V7(7, "1.17", "?", null),
    ;

    public static final PackFormat LATEST = V3;

    private final int format;
    private final String minVersion;
    private final String maxVersion;
    private final PackGeneratorFactory factory;

    PackFormat(int format, String minVersion, String maxVersion, PackGeneratorFactory factory) {
        this.format = format;
        this.minVersion = minVersion;
        this.maxVersion = maxVersion;
        this.factory = factory;
    }

    public int getFormat() {
        return format;
    }

    public String getVersionRange() {
        return String.format("%s-%s", minVersion, maxVersion);
    }

    public PackGeneratorFactory getFactory() {
        return factory;
    }

    @Override
    public String toString() {
        return String.format("MC %s - %s", minVersion, maxVersion);
    }
}