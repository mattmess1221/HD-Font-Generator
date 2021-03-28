package mnm.hdfontgen.pack;

import mnm.hdfontgen.pack.format.BitmapFontGenerator;
import mnm.hdfontgen.pack.format.LegacyFontGenerator;

import java.awt.*;
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

    public static class Builder {
        private final PackFormat format;

        private String description;

        public Builder(PackFormat format) {
            this.format = Objects.requireNonNull(format);
        }

        public Builder description(String desc) {
            this.description = Objects.requireNonNull(desc);
            return this;
        }

        public BitmapBuilder bitmap() {
            return new BitmapBuilder(this);
        }

        public static class BitmapBuilder {

            private final Builder parent;

            private Font font;
            private TextureSize size;
            private boolean unicode;

            public BitmapBuilder(Builder parent) {
                this.parent = parent;
            }

            private String getDescription() {
                if (parent.description != null) {
                    return parent.description;
                }
                var fontName = font.getFontName();
                var withUnicode = unicode ? " with unicode" : "";
                var versions = parent.format.getVersionRange();

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
                checkNotNull(size, "size");;
                return new Bitmap(parent.format, getDescription(), font, size, unicode);
            }
        }
    }

    private static void checkNotNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalStateException(message);
        }
    }
}
