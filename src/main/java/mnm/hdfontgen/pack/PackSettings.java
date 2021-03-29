package mnm.hdfontgen.pack;

import mnm.hdfontgen.pack.format.BitmapFontGenerator;
import mnm.hdfontgen.pack.format.LegacyFontGenerator;
import mnm.hdfontgen.pack.format.TrueTypeFontGenerator;

import java.awt.*;
import java.nio.file.Path;
import java.util.Objects;

public abstract class PackSettings {
    public final PackFormat format;
    public final String description;

    private PackSettings(PackFormat format, String description) {
        this.format = format;
        this.description = description;
    }

    public PackJson getPackJson() {
        return new PackJson(format.getFormat(), description);
    }

    public abstract PackGenerator createGenerator();

    public static class Bitmap extends PackSettings {

        public final Font font;
        public final TextureSize size;
        public final boolean unicode;

        private Bitmap(PackFormat format, String description, Font font, TextureSize size, boolean unicode) {
            super(format, description);
            this.font = font;
            this.size = size;
            this.unicode = unicode;
        }

        public HDFont getFont() {
            return new HDFont(font, size);
        }

        @Override
        public PackGenerator createGenerator() {
            return format.supportsFontProviders() ? new BitmapFontGenerator(this) : new LegacyFontGenerator(this);
        }
    }

    public static class TrueType extends PackSettings {
        public final Path font;
        public final float oversample;

        private TrueType(PackFormat format, String description, Path font, float oversample) {
            super(format, description);
            this.font = font;
            this.oversample = oversample;
        }

        @Override
        public PackGenerator createGenerator() {
            return new TrueTypeFontGenerator(this);
        }
    }

    public static class Builder {
        private final PackFormat format;

        private String description;

        public Builder(PackFormat format) {
            this.format = Objects.requireNonNull(format);
        }

        public Builder withDescription(String desc) {
            this.description = Objects.requireNonNull(desc);
            return this;
        }

        public BitmapBuilder bitmap() {
            return new BitmapBuilder();
        }

        public TrueTypeBuilder trueType() {
            return new TrueTypeBuilder();
        }

        public class BitmapBuilder {

            private Font font;
            private TextureSize size;
            private boolean unicode;

            private String getDescription() {
                if (description != null) {
                    return description;
                }
                var fontName = font.getFontName();
                var withUnicode = unicode ? " with unicode" : "";
                var versions = format.getVersionRange();

                return String.format("%s %s%s for Minecraft %s", fontName, size, withUnicode, versions);
            }

            public BitmapBuilder withFont(Font font) {
                this.font = Objects.requireNonNull(font);
                return this;
            }

            public BitmapBuilder withSize(TextureSize size) {
                this.size = Objects.requireNonNull(size);
                return this;
            }

            public BitmapBuilder withUnicode(boolean unicode) {
                this.unicode = unicode;
                return this;
            }

            public Bitmap build() {
                checkNotNull(font, "font");
                checkNotNull(size, "size");
                return new Bitmap(format, getDescription(), font, size, unicode);
            }
        }

        public class TrueTypeBuilder {
            public Path font;
            public float oversample = 1f;

            private String getDescription() {
                if (description != null) {
                    return description;
                }

                var fontName = font.getFileName().toString();
                var versions = format.getVersionRange();

                return String.format("%s x%.01f for Minecraft %s", fontName, oversample, versions);
            }

            public TrueTypeBuilder withFont(Path fontFile) {
                this.font = Objects.requireNonNull(fontFile);
                return this;
            }

            public TrueTypeBuilder withOversample(float oversample) {
                this.oversample = oversample;
                return this;
            }

            public TrueType build() {
                checkNotNull(font, "font");
                return new TrueType(format, getDescription(), font, oversample);
            }

        }
    }

    private static void checkNotNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalStateException(message);
        }
    }
}
