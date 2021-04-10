package mnm.hdfontgen.pack.generator;

import mnm.hdfontgen.pack.PackSettings;
import mnm.hdfontgen.pack.provider.FontProvider;
import mnm.hdfontgen.pack.provider.FontProvidersJson;

public class FontProviderFontGenerator extends AbstractFontProviderPackGenerator {

    public FontProviderFontGenerator(PackSettings settings) {
        super(settings);
    }

    @Override
    protected void populateFontProviders(FontProvidersJson providers, PackSettings.FontTexture font) {
        for (FontProvider p : font.getProviders(settings.format)) {
            providers.addProvider(p);
        }
    }
}
