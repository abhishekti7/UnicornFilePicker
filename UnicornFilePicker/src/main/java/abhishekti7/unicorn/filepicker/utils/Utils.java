package abhishekti7.unicorn.filepicker.utils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abhishek Tiwari on 07-01-2021.
 */
public class Utils {
    public static Map<Integer, String> mapOfMonths = new HashMap<Integer, String>() {{
        put(1, "Jan");
        put(2, "Feb");
        put(3, "Mar");
        put(4, "Apr");
        put(5, "May");
        put(6, "Jun");
        put(7, "Jul");
        put(8, "Aug");
        put(9, "Sep");
        put(10, "Oct");
        put(11, "Nov");
        put(12, "Dec");
    }};
    public static String longToReadableDate(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        return mapOfMonths.get(calendar.get(Calendar.MONTH) + 1) + " " +
                calendar.get(Calendar.DAY_OF_MONTH) + ", " + calendar.get(Calendar.YEAR);
    }
}
