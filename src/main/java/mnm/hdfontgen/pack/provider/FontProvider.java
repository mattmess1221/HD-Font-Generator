package mnm.hdfontgen.pack.provider;

import mnm.hdfontgen.pack.PackSettings;
import mnm.hdfontgen.pack.resource.Resource;

public abstract class FontProvider<T extends PackSettings> {
    public final String type;

    FontProvider(String type) {
        this.type = type;
    }

    public abstract Resource[] getResources(T settings);
}
