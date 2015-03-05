package mnm.hdfontgen;

import java.util.ArrayList;
import java.util.List;

public enum TextureSize {

    x32(32),
    x64(64),
    x128(128),
    x256(256),
    x512(512);

    private final int textureSize;

    private TextureSize(int size) {
        this.textureSize = size;
    }

    public int getTextureSize() {
        return textureSize;
    }

    public static TextureSize forSize(int size) {
        for (TextureSize tex : values()) {
            if (tex.getTextureSize() == size) {
                return tex;
            }
        }
        return null;
    }

    public static List<Integer> getSizes() {
        List<Integer> list = new ArrayList<>();
        for (TextureSize tex : values()) {
            list.add(tex.getTextureSize());
        }
        return list;
    }
}
