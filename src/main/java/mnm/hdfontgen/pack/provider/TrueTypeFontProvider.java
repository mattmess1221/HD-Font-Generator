package mnm.hdfontgen.pack.provider;

import mnm.hdfontgen.pack.PackSettings;
import mnm.hdfontgen.pack.ResourcePath;
import mnm.hdfontgen.pack.resource.FileResource;
import mnm.hdfontgen.pack.resource.Resource;

import java.nio.file.Path;

class TrueTypeFontProvider extends FontProvider<PackSettings.TrueType> {

    private final ResourcePath file;
    private final float[] shift;
    private final float size;
    private final float oversample;
    private final String skip;

    TrueTypeFontProvider(ResourcePath file, float[] shift, float size, float oversample, String skip) {
        super("ttf");
        this.file = file;
        this.shift = shift;
        this.size = size;
        this.oversample = oversample;
        this.skip = skip;
    }

    private Resource getFileResource(Path input) {
        ResourcePath output = new ResourcePath(file.getNamespace(), "font/" + file.getPath());
        return new FileResource(input, output);
    }

    @Override
    public Resource[] getResources(PackSettings.TrueType settings) {
        return new Resource[] {
                getFileResource(settings.font)
        };
    }
}
