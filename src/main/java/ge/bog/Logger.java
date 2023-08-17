package ge.bog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Logger {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    static {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+4"));
    }

    public static void info(String message) {
        System.out.println(sdf.format(new Date()) + " INFO " + message);
    }

    public static void error(String message, Throwable e) {
        System.out.println(sdf.format(new Date()) + " ERROR " + message);
        e.printStackTrace();
    }
}
