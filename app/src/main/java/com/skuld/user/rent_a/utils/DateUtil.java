package com.skuld.user.rent_a.utils;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static String getTimeStamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    }

    public static Date convertToDate(String dateTimeString) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        Date convertedDate;

        try {
            convertedDate = simpleFormat.parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        return convertedDate;
    }

    public static Date convertToDate(String dateTimeString, String format) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        Date convertedDate;

        try {
            convertedDate = simpleFormat.parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        return convertedDate;
    }

    public static String getHistoryTimeStamp(String dateTimeString) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        SimpleDateFormat formatOut = new SimpleDateFormat("MMM\ndd\nyyyy", Locale.getDefault());
        try {
            Date date = format.parse(dateTimeString);

            return formatOut.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertFormatDate(String dateString, String dateFormat, String outputFormat) {
        SimpleDateFormat inFormat = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        SimpleDateFormat outFormat = new SimpleDateFormat(outputFormat, Locale.ENGLISH);

        try {
            Date date = inFormat.parse(dateString);

            return outFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Calendar convertStringToCalendar(String date) {
        return convertStringToCalendar(date, "MMMM dd yyyy");
    }

    public static Calendar convertStringToCalendar(String date, String template) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat(template, Locale.ENGLISH).parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return calendar;
    }

    public static String getMonthString(int month) {
        return new DateFormatSymbols().getMonths()[month - 1];
    }

    public static String parseBirthday(String dateTimeString, String outFormat) {
        Date date = convertToDate(dateTimeString, "yyyy-MM-dd");

        SimpleDateFormat formatOut = new SimpleDateFormat(outFormat, Locale.ENGLISH);

        return formatOut.format(date);
    }
}
