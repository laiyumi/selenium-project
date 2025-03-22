package com.example.selenium.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static int[] parseDate(String dateStr) throws Exception {
        // Assumes Excel string format like "March 17, 2025"
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
        Date date = sdf.parse(dateStr);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH); // Note: 0-based (January = 0)
        int year = cal.get(Calendar.YEAR);

        return new int[]{day, month, year};
    }
}