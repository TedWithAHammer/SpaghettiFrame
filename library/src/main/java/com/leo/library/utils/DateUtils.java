package com.leo.library.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by leo on 2017/2/13.
 */

public class DateUtils {
    public static final String PATTERN1 = "yyyy-MM-dd hh:mm:ss";

    public static String timeMills2DateString(long timeMills, String pattern) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMills);
        DateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINESE);
        return sdf.format(calendar.getTime());
    }
}
