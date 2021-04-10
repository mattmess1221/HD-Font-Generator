package mnm.hdfontgen.pack.format;

import mnm.hdfontgen.pack.FontPack;
import mnm.hdfontgen.pack.PackSettings;
import mnm.hdfontgen.pack.PackGenerator;

abstract class AbstractPackGenerator implements PackGenerator {

    protected final PackSettings settings;

    protected AbstractPackGenerator(PackSettings settings) {
        this.settings = settings;
    }

    @Override
    public final FontPack generate() {
        var pack = new FontPack();
        pack.addResource(settings.getPackJson());
        populatePack(pack);
        return pack;
    }

    protected abstract void populatePack(FontPack pack);
}
