package mnm.hdfontgen.pack;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum TextureSize {

    x32(32),
    x64(64),
    x128(128),
    x256(256),
    x512(512);

    private final int textureSize;

    TextureSize(int size) {
        this.textureSize = size;
    }

    public int getTextureSize() {
        return textureSize;
    }

    public static Optional<TextureSize> forSize(int size) {
        return Arrays.stream(values())
                .filter((tex) -> tex.getTextureSize() == size)
                .findFirst();
    }

    public static List<Integer> getSizes() {
        return Arrays.stream(values())
                .map(TextureSize::getTextureSize)
                .collect(Collectors.toList());
    }
}
