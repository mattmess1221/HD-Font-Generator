package mnm.hdfontgen.pack.format;

import mnm.hdfontgen.pack.FontPack;
import mnm.hdfontgen.pack.GeneratorSettings;
import mnm.hdfontgen.pack.provider.StandardFontProviders;

public class LegacyFontGenerator extends AbstractPackGenerator {

    @Override
    protected void populatePack(FontPack pack, GeneratorSettings settings) {
        pack.addResources(StandardFontProviders.ascii().getResources(settings));
        if (settings.unicode) {
            pack.addResources(StandardFontProviders.unicodePages().getResources(settings));
        }
    }
}
