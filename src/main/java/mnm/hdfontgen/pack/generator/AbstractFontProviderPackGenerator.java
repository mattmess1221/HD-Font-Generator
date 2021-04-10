package mnm.hdfontgen.pack.generator;

import mnm.hdfontgen.pack.FontPack;
import mnm.hdfontgen.pack.PackSettings;
import mnm.hdfontgen.pack.ResourcePath;
import mnm.hdfontgen.pack.provider.FontProvider;
import mnm.hdfontgen.pack.provider.FontProvidersJson;

import java.util.Map;

abstract class AbstractFontProviderPackGenerator extends AbstractPackGenerator {
    AbstractFontProviderPackGenerator(PackSettings settings) {
        super(settings);
    }

    @Override
    protected final void populatePack(FontPack pack) {
        for (Map.Entry<ResourcePath, PackSettings.FontTexture> e : settings.fonts.entrySet()) {

            var providers = new FontProvidersJson(e.getKey());
            populateFontProviders(providers, e.getValue());

            pack.addResource(providers);
            for (FontProvider provider : providers.getProviders()) {
                pack.addResources(provider.getResources());
            }
        }
    }

    protected abstract void populateFontProviders(FontProvidersJson providers, PackSettings.FontTexture font);
}
