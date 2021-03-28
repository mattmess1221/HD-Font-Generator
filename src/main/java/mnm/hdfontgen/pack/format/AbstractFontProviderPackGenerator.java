package mnm.hdfontgen.pack.format;

import mnm.hdfontgen.pack.FontPack;
import mnm.hdfontgen.pack.GeneratorSettings;
import mnm.hdfontgen.pack.provider.FontProvider;
import mnm.hdfontgen.pack.provider.FontProvidersJson;

abstract class AbstractFontProviderPackGenerator extends AbstractPackGenerator {
    @Override
    protected final void populatePack(FontPack pack, GeneratorSettings settings) {
        var providers = new FontProvidersJson("minecraft", "default");
        populateFontProviders(providers, settings);

        pack.addResource(providers);
        for (FontProvider provider : providers.getProviders()) {
            pack.addResources(provider.getResources(settings));
        }
    }

    protected abstract void populateFontProviders(FontProvidersJson providers, GeneratorSettings settings);
}
