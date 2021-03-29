package mnm.hdfontgen.pack.format;

import mnm.hdfontgen.pack.PackSettings;
import mnm.hdfontgen.pack.provider.FontProvidersJson;
import mnm.hdfontgen.pack.provider.StandardFontProviders;

import java.util.Locale;

public class TrueTypeFontGenerator extends AbstractFontProviderPackGenerator<PackSettings.TrueType> {

    public TrueTypeFontGenerator(PackSettings.TrueType settings) {
        super(settings);
    }

    @Override
    protected void populateFontProviders(FontProvidersJson<PackSettings.TrueType> providers) {
        var file = settings.font.getFileName().toString().toLowerCase(Locale.ROOT);
        var oversample = settings.oversample;
        providers.addProvider(StandardFontProviders.trueType(file, oversample));
    }

}
