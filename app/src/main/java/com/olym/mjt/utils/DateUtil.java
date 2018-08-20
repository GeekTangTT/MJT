package com.olym.mjt.utils;

import android.content.res.Resources;
import com.olym.mjt.module.MjtApplication;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil
{
    public static String convert_before(long paramLong)
    {
        if (paramLong < 0L) {
            return String.valueOf(paramLong);
        }
        Calendar localCalendar1 = Calendar.getInstance();
        Calendar localCalendar2 = Calendar.getInstance();
        localCalendar2.setTimeInMillis(paramLong);
        if ((localCalendar2.get(1) == localCalendar1.get(1)) && (localCalendar2.get(2) == localCalendar1.get(2)) && (localCalendar2.get(5) == localCalendar1.get(5))) {
            return new SimpleDateFormat("HH:mm").format(localCalendar2.getTime());
        }
        if ((localCalendar2.get(1) == localCalendar1.get(1)) && (localCalendar2.get(2) == localCalendar1.get(2)) && (localCalendar2.get(5) == localCalendar1.get(5) - 1)) {
            return MjtApplication.getInstance().getResources().getString(2131689698);
        }
        return new SimpleDateFormat("yyyy/M/d").format(localCalendar2.getTime());
    }

    public static String getCurrentTime()
    {
        Calendar localCalendar = Calendar.getInstance();
        return new SimpleDateFormat("yy���M���d���").format(localCalendar.getTime());
    }

    public static String getTimeForDate(long paramLong)
    {
        Calendar localCalendar1 = Calendar.getInstance();
        Calendar localCalendar2 = Calendar.getInstance();
        localCalendar2.setTimeInMillis(paramLong);
        if ((localCalendar2.get(1) == localCalendar1.get(1)) && (localCalendar2.get(2) == localCalendar1.get(2)) && (localCalendar2.get(5) == localCalendar1.get(5))) {
            return MjtApplication.getInstance().getResources().getString(2131689697);
        }
        if ((localCalendar2.get(1) == localCalendar1.get(1)) && (localCalendar2.get(2) == localCalendar1.get(2)) && (localCalendar2.get(5) == localCalendar1.get(5) - 1)) {
            return MjtApplication.getInstance().getResources().getString(2131689698);
        }
        if (localCalendar2.get(1) == localCalendar1.get(1)) {
            return new SimpleDateFormat("M/d").format(localCalendar2.getTime());
        }
        return new SimpleDateFormat("yy/M/d").format(localCalendar2.getTime());
    }

    public static String getTimeForHHMM(long paramLong)
    {
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTimeInMillis(paramLong);
        return new SimpleDateFormat("HH:mm").format(localCalendar.getTime());
    }

    public static String getTimeForYMD(long paramLong)
    {
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTimeInMillis(paramLong);
        return new SimpleDateFormat("yyyy/M/d").format(localCalendar.getTime());
    }

    public static boolean isSameDay(long paramLong1, long paramLong2)
    {
        Calendar localCalendar1 = Calendar.getInstance();
        localCalendar1.setTimeInMillis(paramLong1);
        Calendar localCalendar2 = Calendar.getInstance();
        localCalendar2.setTimeInMillis(paramLong2);
        return (localCalendar1.get(1) == localCalendar2.get(1)) && (localCalendar1.get(2) == localCalendar2.get(2)) && (localCalendar1.get(5) == localCalendar2.get(5));
    }

    public static int sk_time_current_time()
    {
        return (int)(System.currentTimeMillis() / 1000L);
    }
}
