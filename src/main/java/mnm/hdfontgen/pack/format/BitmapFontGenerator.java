package mnm.hdfontgen.pack.format;

import mnm.hdfontgen.pack.PackSettings;
import mnm.hdfontgen.pack.provider.FontProvidersJson;
import mnm.hdfontgen.pack.provider.StandardFontProviders;

public class BitmapFontGenerator extends AbstractFontProviderPackGenerator<PackSettings.Bitmap> {

    public BitmapFontGenerator(PackSettings.Bitmap settings) {
        super(settings);
    }

    @Override
    protected void populateFontProviders(FontProvidersJson<PackSettings.Bitmap> providers) {
        providers.addProvider(StandardFontProviders.ascii());
        providers.addProvider(StandardFontProviders.accented());
        providers.addProvider(StandardFontProviders.nonLatinEuropean());

        if (settings.unicode) {
            providers.addProvider(StandardFontProviders.unicodePages());
        }
    }
}
