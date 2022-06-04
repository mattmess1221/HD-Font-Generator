package mnm.hdfontgen.pack.generator;

import java.util.Map;

import mnm.hdfontgen.pack.FontPack;
import mnm.hdfontgen.pack.PackSettings;
import mnm.hdfontgen.pack.ResourcePath;
import mnm.hdfontgen.pack.provider.FontProvidersJson;

abstract class AbstractFontProviderPackGenerator extends AbstractPackGenerator {
    AbstractFontProviderPackGenerator(PackSettings settings) {
        super(settings);
    }

    @Override
    protected final void populatePack(FontPack pack) {
        for (Map.Entry<ResourcePath, PackSettings.FontTexture> e : settings.fonts.entrySet()) {

            var providers = new FontProvidersJson(e.getKey());
            populateFontProviders(providers, e.getValue());
            providers.setup(pack);
        }
    }

    protected abstract void populateFontProviders(FontProvidersJson providers, PackSettings.FontTexture font);
}
