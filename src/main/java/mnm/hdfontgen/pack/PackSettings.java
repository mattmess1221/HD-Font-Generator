package mnm.hdfontgen.pack;

import mnm.hdfontgen.pack.format.FontProviderFontGenerator;
import mnm.hdfontgen.pack.format.LegacyFontGenerator;
import mnm.hdfontgen.pack.provider.FontProvider;
import mnm.hdfontgen.pack.provider.FontProvidersJson;
import mnm.hdfontgen.pack.provider.StandardFontProviders;

import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class PackSettings {
    public final PackFormat format;
    public final String description;
    public final Map<ResourcePath, FontTexture> fonts;

    private PackSettings(PackFormat format, String description, Map<ResourcePath, FontTexture> fonts) {
        this.format = format;
        this.description = description;
        this.fonts = Map.copyOf(fonts);
    }

    public PackJson getPackJson() {
        return new PackJson(format.getFormat(), description);
    }

    public PackGenerator createGenerator() {
        if (!format.supportsFontProviders()) {
            return new LegacyFontGenerator(this);
        }
        return new FontProviderFontGenerator(this);
    }

    public abstract static class FontTexture {
        public final ResourcePath name;

        protected FontTexture(ResourcePath name) {
            this.name = name;
        }

        public abstract List<FontProvider> getProviders(PackFormat format);
    }

    public static class Bitmap extends FontTexture {
        public final Font font;
        public final TextureSize size;
        public final boolean unicode;

        private Bitmap(ResourcePath name, Font font, TextureSize size, boolean unicode) {
            super(name);
            this.font = font;
            this.size = size;
            this.unicode = unicode;
        }

        public HDFont getFont() {
            return new HDFont(font, size);
        }

        @Override
        public List<FontProvider> getProviders(PackFormat format) {
            List<FontProvider> providers = new ArrayList<>(4);
            providers.add(StandardFontProviders.ascii(this));
            if (format.supportsFontProviders()) {
                providers.add(StandardFontProviders.nonLatinEuropean(this));
                providers.add(StandardFontProviders.accented(this));
            }
            if (this.unicode) {
                providers.add(StandardFontProviders.unicodePages(this));
            }
            return providers;
        }
    }

    public static class TrueType extends FontTexture {
        public final Path font;
        public final float oversample;

        private TrueType(ResourcePath name, Path font, float oversample) {
            super(name);
            this.font = font;
            this.oversample = oversample;
        }

        @Override
        public List<FontProvider> getProviders(PackFormat format) {
            return List.of(
                    StandardFontProviders.trueType(this)
            );
        }
    }

    public static class Builder implements BuilderBase<PackSettings> {
        private final PackFormat format;
        private String description;
        private Map<ResourcePath, FontTexture> fonts = new HashMap<>();

        public Builder(PackFormat format) {
            this.format = Objects.requireNonNull(format);
        }

        public Builder withDescription(String desc) {
            this.description = Objects.requireNonNull(desc);
            return this;
        }

        public Builder bitmap(ResourcePath name, UnaryOperator<BitmapBuilder> func) {
            return addFontProvider(name, new BitmapBuilder(name), func);
        }

        public Builder trueType(ResourcePath name, UnaryOperator<TrueTypeBuilder> func) {
            if (!this.format.supportsTrueTypeFonts()) {
                throw new UnsupportedOperationException("Pack format " + this.format + " does not support true type fonts.");
            }
            return addFontProvider(name, new TrueTypeBuilder(name), func);
        }

        protected <T extends FontTexture, B extends BuilderBase<T>>
        Builder addFontProvider(ResourcePath name, B builder, UnaryOperator<B> func) {
            // make sure this name hasn't been registered already
            if (this.fonts.containsKey(name)) {
                throw new UnsupportedOperationException("Provider named " + name + " was already created.");
            }
            // named fonts are only supported in V6 and up
            if (!this.format.supportsNamedFonts() && !FontProvidersJson.DEFAULT_NAME.equals(name)) {
                throw new UnsupportedOperationException("Pack format " + this.format + " does not support named fonts. Only " + FontProvidersJson.DEFAULT_NAME + " is supported.");
            }

            this.fonts.put(name, func.apply(builder).build());
            return this;
        }

        @Override
        public PackSettings build() {
            checkNotNull(this.description, "description");
            check(this.fonts, Predicate.not(Map::isEmpty), "fonts is empty");
            return new PackSettings(format, description, fonts);
        }


        public class BitmapBuilder implements BuilderBase<Bitmap> {

            private final ResourcePath name;
            private Font font;
            private TextureSize size;
            private boolean unicode;

            BitmapBuilder(ResourcePath name) {
                this.name = name;
            }

            private String makeDescription() {
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

            @Override
            public Bitmap build() {
                checkNotNull(font, "font");
                checkNotNull(size, "size");
                if (description == null) {
                    description = makeDescription();
                }
                return new Bitmap(name, font, size, unicode);
            }
        }

        public class TrueTypeBuilder implements BuilderBase<TrueType> {
            private ResourcePath name;
            private Path font;
            private float oversample = 1f;

            TrueTypeBuilder(ResourcePath name) {
                this.name = name;
            }

            private String makeDescription() {
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

            @Override
            public TrueType build() {
                checkNotNull(font, "font");
                if (description == null) {
                    description = makeDescription();
                }
                return new TrueType(name, font, oversample);
            }
        }
    }

    private static void checkNotNull(Object obj, String message) {
        check(obj, Objects::nonNull, message);
    }

    private static <T> T check(T obj, Predicate<T> condition, String message) {
        if (!condition.test(obj)) {
            throw new IllegalStateException(message);
        }
        return obj;
    }

    private interface BuilderBase<T> {
        T build();
    }
}
