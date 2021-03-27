package mnm.hdfontgen.pack.provider;

import mnm.hdfontgen.pack.resource.AbstractJsonResource;
import mnm.hdfontgen.pack.ResourcePath;
import mnm.hdfontgen.pack.ZipPath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FontProvidersJson extends AbstractJsonResource {

    private transient final ResourcePath path;
    private final List<FontProvider> providers;

    public FontProvidersJson(String namespace, String name) {
        this.path = new ResourcePath(namespace, String.format("font/%s.json", name));
        this.providers = new ArrayList<>();
    }

    @Override
    public ZipPath getPath() {
        return path;
    }

    public void addProvider(FontProvider provider) {
        this.providers.add(provider);
    }

    public List<FontProvider> getProviders() {
        return Collections.unmodifiableList(providers);
    }

}
