package ua.edu.sumdu.j2se.rudenko.tasks.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

    public static boolean validDate(String dateString) {
        return DateUtil.stringToDate(dateString) != null;
    }
}