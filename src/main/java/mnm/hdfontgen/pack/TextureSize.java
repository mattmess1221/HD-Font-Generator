package mnm.hdfontgen.pack;

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
}
