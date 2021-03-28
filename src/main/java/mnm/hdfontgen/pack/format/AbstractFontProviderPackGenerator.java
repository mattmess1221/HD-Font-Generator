package mnm.hdfontgen.pack.format;

import mnm.hdfontgen.pack.FontPack;
import mnm.hdfontgen.pack.PackSettings;
import mnm.hdfontgen.pack.provider.FontProvider;
import mnm.hdfontgen.pack.provider.FontProvidersJson;

abstract class AbstractFontProviderPackGenerator<T extends PackSettings> extends AbstractPackGenerator<T> {
    AbstractFontProviderPackGenerator(T settings) {
        super(settings);
    }

    @Override
    protected final void populatePack(FontPack pack) {
        var providers = new FontProvidersJson<T>("minecraft", "default");
        populateFontProviders(providers);

        pack.addResource(providers);
        for (FontProvider<T> provider : providers.getProviders()) {
            pack.addResources(provider.getResources(settings));
        }
    }

    protected abstract void populateFontProviders(FontProvidersJson<T> providers);
}
