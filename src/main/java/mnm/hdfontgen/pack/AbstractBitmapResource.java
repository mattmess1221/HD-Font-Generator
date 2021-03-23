package mnm.hdfontgen.pack;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class AbstractBitmapResource implements Resource {

    protected abstract BufferedImage render();

    @Override
    public final void writeTo(Path path) throws IOException {
        try (var out = Files.newOutputStream(path)) {
            ImageIO.write(this.render(), "png", out);
        }
    }
}
