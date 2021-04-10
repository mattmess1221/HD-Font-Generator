package mnm.hdfontgen.pack.format;

import mnm.hdfontgen.pack.FontPack;
import mnm.hdfontgen.pack.PackSettings;
import mnm.hdfontgen.pack.provider.FontProvidersJson;

public class LegacyFontGenerator extends AbstractPackGenerator {

    public LegacyFontGenerator(PackSettings settings) {
        super(settings);
    }

    @Override
    protected void populatePack(FontPack pack) {
        // minecraft:default should be the only provider available. The actual
        // name doesn't matter since font providers aren't supported.
        var fontTexture = settings.fonts.get(FontProvidersJson.DEFAULT_NAME);

        var providers = fontTexture.getProviders(settings.format);
        for (var provider : providers) {
            pack.addResources(provider.getResources());
        }
    }
}
