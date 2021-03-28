package mnm.hdfontgen.pack.format;

import mnm.hdfontgen.pack.GeneratorSettings;
import mnm.hdfontgen.pack.provider.FontProvidersJson;
import mnm.hdfontgen.pack.provider.StandardFontProviders;

public class BitmapFontGenerator extends AbstractFontProviderPackGenerator {

    @Override
    protected void populateFontProviders(FontProvidersJson providers, GeneratorSettings settings) {
        providers.addProvider(StandardFontProviders.ascii());
        providers.addProvider(StandardFontProviders.accented());
        providers.addProvider(StandardFontProviders.nonLatinEuropean());

        if (settings.unicode) {
            providers.addProvider(StandardFontProviders.unicodePages());
        }
    }
}
