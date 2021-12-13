package ua.edu.sumdu.j2se.rudenko.tasks.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String dateToString(LocalDateTime currentDateTime) {
        if (currentDateTime == null) {
            return null;
        }
        return currentDateTime.format(formatter);
    }

    public static LocalDateTime stringToDate(String date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.parse(date, formatter);
    }

    public static LocalTime stringToTime(String time) {
        if (time == null) {
            return null;
        }
        return LocalTime.parse(time,DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public static boolean validDate(String dateString) {
        try {
            return DateUtil.stringToDate(dateString) != null;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean validTime(String timeString) {
        try {
            return LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm:ss")) != null;
        }
        catch (Exception e) {
            return false;
        }
    }
}