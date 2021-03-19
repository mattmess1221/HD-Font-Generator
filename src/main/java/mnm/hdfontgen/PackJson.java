package mnm.hdfontgen;

import com.google.gson.annotations.SerializedName;

public class PackJson {
    final PackSection pack;

    PackJson(PackSection pack) {
        this.pack = pack;
    }
}

class PackSection {
    @SerializedName("pack_format")
    final int packFormat;
    final String description;

    PackSection(int packFormat, String description) {
        this.packFormat = packFormat;
        this.description = description;
    }
}