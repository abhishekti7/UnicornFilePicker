package zeus0789.unicorn.filepicker.utils;

import java.util.Calendar;
import java.util.Map;

/**
 * Created by Abhishek Tiwari on 07-01-2021.
 */
public class Utils {
    public static Map<Integer, String> mapOfMonths = Map.ofEntries(
            Map.entry(1, "Jan"),
            Map.entry(2, "Feb"),
            Map.entry(3, "Mar"),
            Map.entry(4, "Apr"),
            Map.entry(5, "May"),
            Map.entry(6, "Jun"),
            Map.entry(7, "Jul"),
            Map.entry(8, "Aug"),
            Map.entry(9, "Sep"),
            Map.entry(10, "Oct"),
            Map.entry(11, "Nov"),
            Map.entry(12, "Dec")
    );

    public static String longToReadableDate(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        return mapOfMonths.get(calendar.get(Calendar.MONTH) + 1) + " " +
                calendar.get(Calendar.DAY_OF_MONTH) + ", " + calendar.get(Calendar.YEAR);
    }
}
