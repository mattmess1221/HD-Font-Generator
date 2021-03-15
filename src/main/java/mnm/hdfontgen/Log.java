package mnm.hdfontgen;

import java.util.ArrayList;
import java.util.List;

public class Log {
    private static final List<ILogger> loggers = new ArrayList<>();

    public static void addLogger(ILogger logger) {
        loggers.add(logger);
    }

    public static void log(String msg, Object... args) {
        String formattedMessage = String.format(msg, args);
        loggers.forEach(log -> log.log(formattedMessage));
    }

    public interface ILogger {
        void log(String msg);
    }
}
