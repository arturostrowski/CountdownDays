package pl.almestinio.countdowndays.util;

import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 * Created by mesti193 on 3/31/2018.
 */

public class DateUtil {

    public static int getDifferenceBetweenTwoDates(DateTime startDay, DateTime endDay){

        int daysBetween = Days.daysBetween(new DateTime(startDay), new DateTime(endDay)).getDays();

        return daysBetween;
    }

}
