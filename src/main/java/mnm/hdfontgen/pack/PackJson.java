package mnm.hdfontgen.pack;

import com.google.gson.annotations.SerializedName;

import mnm.hdfontgen.pack.resource.AbstractJsonResource;

public class PackJson extends AbstractJsonResource {

    final PackSection pack;

    public PackJson(int packFormat, String description) {
        pack = new PackSection(packFormat, description);
    }

    @Override
    public ZipPath getPath() {
        return () -> "pack.mcmeta";
    }

    record PackSection(@SerializedName("pack_format") int packFormat, String description) {
    }
}
