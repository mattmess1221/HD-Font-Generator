package mnm.hdfontgen;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Consumer;

public class IOUtils {

    public static <T> Consumer<T> consume(IOConsumer<T> consumer) {
        return obj -> {
            try {
                consumer.accept(obj);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        };
    }

    public interface IOConsumer<T> {
        void accept(T obj) throws IOException;
    }
}
