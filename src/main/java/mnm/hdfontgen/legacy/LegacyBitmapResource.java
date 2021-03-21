package mnm.hdfontgen.legacy;

import mnm.hdfontgen.pack.Resource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class LegacyBitmapResource implements Resource {

    protected abstract BufferedImage render();

    @Override
    public final void writeTo(Path path) throws IOException {
        try (var out = Files.newOutputStream(path)) {
            ImageIO.write(this.render(), "png", out);
        }
    }
}
