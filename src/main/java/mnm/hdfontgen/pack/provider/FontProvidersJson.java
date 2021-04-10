package mnm.hdfontgen.pack.provider;

import mnm.hdfontgen.pack.ResourcePath;
import mnm.hdfontgen.pack.ZipPath;
import mnm.hdfontgen.pack.resource.AbstractJsonResource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FontProvidersJson extends AbstractJsonResource {
    public static final ResourcePath DEFAULT_NAME = new ResourcePath("minecraft", "default");

    private transient final ResourcePath path;
    private final List<FontProvider> providers;

    public FontProvidersJson(ResourcePath name) {
        this.path = new ResourcePath(name.getNamespace(), String.format("font/%s.json", name.getPath()));
        this.providers = new ArrayList<>();
    }

    @Override
    public ZipPath getPath() {
        return path;
    }

    public void addProvider(FontProvider provider) {
        this.providers.add(provider);
    }

    public List<? extends FontProvider> getProviders() {
        return Collections.unmodifiableList(providers);
    }

}
