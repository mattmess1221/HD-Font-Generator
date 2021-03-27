package mnm.hdfontgen.pack.provider;

import mnm.hdfontgen.pack.GeneratorSettings;
import mnm.hdfontgen.pack.resource.Resource;

public abstract class FontProvider {
    public final String type;

    FontProvider(String type) {
        this.type = type;
    }

    public abstract Resource[] getResources(GeneratorSettings settings);
}
