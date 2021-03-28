package mnm.hdfontgen.pack;

/**
 * The format version of a resource pack.
 */
public enum PackFormat {
    // legacy formats
    V1(1, "1.6.1", "1.8.9"),
    V2(2, "1.9", "1.10.2"),
    V3(3, "1.11", "1.12.2"),
    // supports font providers
    V4(4, "1.13", "1.14.4"),
    // supports truetype fonts
    V5(5, "1.15", "1.16.1"),
    // supports named fonts
    V6(6, "1.16.2", "1.16.5"),
    V7(7, "1.17", null),
    ;

    public static final PackFormat LATEST = V7;

    private final int format;
    private final String minVersion;
    private final String maxVersion;

    PackFormat(int format, String minVersion, String maxVersion) {
        this.format = format;
        this.minVersion = minVersion;
        this.maxVersion = maxVersion;
    }

    /**
     * Gets the format number of this PackFormat.
     *
     * @return The format number
     */
    public int getFormat() {
        return format;
    }

    /**
     * Gets the version range supported by this PackFormat. If the latest
     * version as of release
     *
     * @return
     */
    public String getVersionRange() {
        if (maxVersion == null) {
            return String.format("%s+", minVersion);
        }
        return String.format("%s-%s", minVersion, maxVersion);
    }

    /**
     * Returns the user friendly version range supported by this format.
     *
     * @return The user friendly version range
     */
    @Override
    public String toString() {
        if (maxVersion == null) {
            return String.format("MC %s+", minVersion);
        }
        return String.format("MC %s - %s", minVersion, maxVersion);
    }

    /**
     * Returns true if this format supports font providers. Font providers can
     * be used to customize how a font is rendered, the location of the font
     * resources, and the characters supported by it.
     *
     * @return true if font provders are supported
     */
    public boolean supportsFontProviders() {
        return this.format >= V4.format;
    }

    /**
     * Returns true if this format supports true type fonts.
     *
     * @return true if true type fonts are supported
     */
    public boolean supportsTrueTypeFonts() {
        return this.format >= V5.format;
    }

    /**
     * Returns true if this format supports named fonts. Named fonts can be
     * specified in the {@code font} property of a text component.
     *
     * @return true if named fonts are supported
     */
    public boolean supportsNamedFonts() {
        return this.format >= V6.format;
    }
}