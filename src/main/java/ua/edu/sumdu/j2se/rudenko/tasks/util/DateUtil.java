package ua.edu.sumdu.j2se.rudenko.tasks.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
        return LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public static boolean validDate(String dateString) {
        try {
            return DateUtil.stringToDate(dateString) != null;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean validTime(String timeString) {
        try {
            return LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm:ss")) != null;
        } catch (Exception e) {
            return false;
        }
    }

    public static int timeToSeconds(String time) {
        int hour = Integer.parseInt(time.substring(0, 2));
        int minutes = Integer.parseInt(time.substring(3));
        int seconds = hour * 3600;
        seconds += minutes * 60;
        return seconds;
    }

    public static String secondsToTime(int seconds) {
        int hour = seconds / 3600;
        int minutes = (seconds - hour * 3600) / 60;
        if (String.valueOf(hour).length() == 1) {
            if (String.valueOf(minutes).length() == 1)
                return "0" + hour + ":" + "0" + minutes;
            return "0" + hour + ":" + minutes;
        }
        if (String.valueOf(minutes).length() == 1)
            return hour + ":" + "0" + minutes;
        return hour + ":" + minutes;
    }

    public static boolean validTimeInterval(String time) {
        if (time.length() != 5) {
            return false;
        }
        try {
            if (time.charAt(2) != ':')
                return false;
            if (Integer.parseInt(time.substring(0, 2)) < 0 || Integer.parseInt(time.substring(3)) < 0)
                return false;
            if (Integer.parseInt(time.substring(3)) >= 60)
                return false;
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}