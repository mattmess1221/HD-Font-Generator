package mnm.hdfontgen.pack.format;

import mnm.hdfontgen.pack.FontPack;
import mnm.hdfontgen.pack.GeneratorSettings;
import mnm.hdfontgen.pack.PackGenerator;

abstract class AbstractPackGenerator implements PackGenerator {
    @Override
    public final FontPack generate(GeneratorSettings settings) {
        var pack = new FontPack();
        pack.addResource(settings.getPackJson());
        populatePack(pack, settings);
        return pack;
    }

    protected abstract void populatePack(FontPack pack, GeneratorSettings settings);
}
