package mnm.hdfontgen.pack.format;

import mnm.hdfontgen.pack.FontPack;
import mnm.hdfontgen.pack.provider.FontProvidersJson;
import mnm.hdfontgen.pack.GeneratorSettings;
import mnm.hdfontgen.pack.PackGenerator;
import mnm.hdfontgen.pack.provider.StandardFontProviders;

class BitmapFontGenerator implements PackGenerator {
    @Override
    public FontPack generate(GeneratorSettings settings) {
        var pack = new FontPack();
        pack.addResource(settings.getPackJson());

        FontProvidersJson providers = new FontProvidersJson("minecraft", "default");
        providers.addProvider(StandardFontProviders.ascii());
        providers.addProvider(StandardFontProviders.accented());
        providers.addProvider(StandardFontProviders.nonLatinEuropean());

        if (settings.unicode) {
            providers.addProvider(StandardFontProviders.unicodePages());
        }

        pack.addResource(providers);
        for (var provider : providers.getProviders()) {
            pack.addResources(provider.getResources(settings));
        }

        return pack;
    }
}
