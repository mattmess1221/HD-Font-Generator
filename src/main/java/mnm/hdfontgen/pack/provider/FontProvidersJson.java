package mnm.hdfontgen.pack.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mnm.hdfontgen.pack.FontPack;
import mnm.hdfontgen.pack.ResourcePath;
import mnm.hdfontgen.pack.ZipPath;
import mnm.hdfontgen.pack.resource.AbstractJsonResource;

public class FontProvidersJson extends AbstractJsonResource {
    public static final ResourcePath DEFAULT_NAME = new ResourcePath("minecraft", "default");

    private transient final ResourcePath path;
    private final List<FontProvider> providers;

    public FontProvidersJson(ResourcePath name) {
        this.path = new ResourcePath(name.namespace(), String.format("font/%s.json", name.path()));
        this.providers = new ArrayList<>();
    }

    @Override
    public ZipPath getPath() {
        return path;
    }

    public void addProvider(FontProvider provider) {
        this.providers.add(provider);
    }

    public void addProviders(Collection<? extends FontProvider> providers) {
        this.providers.addAll(providers);
    }

    public void setup(FontPack pack) {
        pack.addResource(this);
        for (FontProvider provider : providers) {
            provider.setup(pack);
        }
    }
}
