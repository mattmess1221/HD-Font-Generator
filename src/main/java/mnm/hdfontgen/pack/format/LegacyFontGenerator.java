package mnm.hdfontgen.pack.format;

import mnm.hdfontgen.pack.FontPack;
import mnm.hdfontgen.pack.PackSettings;
import mnm.hdfontgen.pack.provider.StandardFontProviders;

public class LegacyFontGenerator extends AbstractPackGenerator<PackSettings.Bitmap> {

    public LegacyFontGenerator(PackSettings.Bitmap settings) {
        super(settings);
    }

    @Override
    protected void populatePack(FontPack pack) {
        pack.addResources(StandardFontProviders.ascii().getResources(settings));
        if (settings.unicode) {
            pack.addResources(StandardFontProviders.unicodePages().getResources(settings));
        }
    }
}
