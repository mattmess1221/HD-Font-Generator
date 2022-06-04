package mnm.hdfontgen.pack.provider;

import mnm.hdfontgen.pack.FontPack;

public abstract class FontProvider {
    public final String type;

    FontProvider(String type) {
        this.type = type;
    }

    public abstract void setup(FontPack pack);

}
