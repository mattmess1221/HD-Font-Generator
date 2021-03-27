package mnm.hdfontgen.pack.format;

import mnm.hdfontgen.pack.FontPack;
import mnm.hdfontgen.pack.GeneratorSettings;
import mnm.hdfontgen.pack.PackGenerator;
import mnm.hdfontgen.pack.provider.StandardFontProviders;

class LegacyFontGenerator implements PackGenerator {

    @Override
    public FontPack generate(GeneratorSettings settings) {
        FontPack pack = new FontPack();

        pack.addResource(settings.getPackJson());
        pack.addResources(StandardFontProviders.ascii().getResources(settings));
        if (settings.unicode) {
            pack.addResources(StandardFontProviders.unicodePages().getResources(settings));
        }

        return pack;
    }

}
