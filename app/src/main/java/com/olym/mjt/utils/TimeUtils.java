package com.olym.mjt.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;
import com.lc.methodex.LogFinalUtils;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.utils.sharedpreferencesutils.XmppSpUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils
{
    public static final SimpleDateFormat f_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat friendly_format1 = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat friendly_format2 = new SimpleDateFormat("MM-dd HH:mm");
    private static SimpleDateFormat hm_formater = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat s_format;
    public static final SimpleDateFormat sk_format_1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat ss_format = new SimpleDateFormat("MM-dd");

    static
    {
        s_format = new SimpleDateFormat("yyyy-MM-dd");
    }

    public static String f_long_2_str(long paramLong)
    {
        return f_format.format(new Date(paramLong));
    }

    public static long f_str_2_long(String paramString)
    {
        try
        {
            long l = f_format.parse(paramString).getTime();
            return l;
        }
        catch (ParseException paramString)
        {
            paramString.printStackTrace();
            LogFinalUtils.logForException(paramString);
        }
        return 0L;
    }

    public static String getChatSimpleDate(String paramString)
    {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yy-MM-dd");
        try
        {
            paramString = localSimpleDateFormat.format(f_format.parse(paramString));
            return paramString;
        }
        catch (ParseException paramString)
        {
            paramString.printStackTrace();
        }
        return "";
    }

    public static String getCurrentTime()
    {
        return f_format.format(new Date(System.currentTimeMillis()));
    }

    public static String getCurrentTimeMask(long paramLong)
    {
        return f_format.format(new Date(System.currentTimeMillis() + paramLong));
    }

    public static int getDayOfMonth(String paramString)
    {
        try
        {
            int i = s_format.parse(paramString).getDate();
            return i;
        }
        catch (ParseException paramString)
        {
            paramString.printStackTrace();
        }
        return 0;
    }

    public static String getFriendlyTimeDesc(Context paramContext, int paramInt)
    {
        String str2 = "";
        Date localDate1 = new Date(paramInt * 1000L);
        Date localDate2 = new Date();
        long l = localDate2.getTime() / 1000L - paramInt;
        String str1;
        if (l < 10L) {
            str1 = paramContext.getString(2131689808);
        }
        for (;;)
        {
            paramContext = str1;
            if (TextUtils.isEmpty(str1)) {
                paramContext = friendly_format2.format(localDate1);
            }
            return paramContext;
            if (l <= 60L)
            {
                str1 = l + paramContext.getString(2131689806);
            }
            else if (l < 1800L)
            {
                str1 = l / 60L + paramContext.getString(2131689805);
            }
            else if (l < 86400L)
            {
                if (localDate2.getDay() - localDate1.getDay() == 0) {
                    str1 = friendly_format1.format(localDate1);
                } else {
                    str1 = paramContext.getString(2131689809) + " " + friendly_format1.format(localDate1);
                }
            }
            else if (l < 172800L)
            {
                if ((localDate2.getDay() - localDate1.getDay() == 1) || (localDate2.getDay() - localDate1.getDay() == -6)) {
                    str1 = paramContext.getString(2131689809) + " " + friendly_format1.format(localDate1);
                } else {
                    str1 = paramContext.getString(2131689807) + " " + friendly_format1.format(localDate1);
                }
            }
            else
            {
                str1 = str2;
                if (l < 259200L) {
                    if (localDate2.getDay() - localDate1.getDay() != 2)
                    {
                        str1 = str2;
                        if (localDate2.getDay() - localDate1.getDay() != -5) {}
                    }
                    else
                    {
                        str1 = paramContext.getString(2131689807) + " " + friendly_format1.format(localDate1);
                    }
                }
            }
        }
    }

    public static int getHours(String paramString)
    {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        try
        {
            int i = localSimpleDateFormat.parse(paramString).getHours();
            return i;
        }
        catch (ParseException paramString)
        {
            paramString.printStackTrace();
        }
        return 0;
    }

    public static int getMinutes(String paramString)
    {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        try
        {
            int i = localSimpleDateFormat.parse(paramString).getMinutes();
            return i;
        }
        catch (ParseException paramString)
        {
            paramString.printStackTrace();
        }
        return 0;
    }

    public static int getMonth(String paramString)
    {
        try
        {
            int i = s_format.parse(paramString).getMonth();
            return i;
        }
        catch (ParseException paramString)
        {
            paramString.printStackTrace();
        }
        return 0;
    }

    public static int getSeconds(String paramString)
    {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        try
        {
            int i = localSimpleDateFormat.parse(paramString).getSeconds();
            return i;
        }
        catch (ParseException paramString)
        {
            paramString.printStackTrace();
        }
        return 0;
    }

    public static String getSimpleDate(String paramString)
    {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            paramString = localSimpleDateFormat.format(f_format.parse(paramString));
            return paramString;
        }
        catch (ParseException paramString)
        {
            paramString.printStackTrace();
        }
        return "";
    }

    public static String getSimpleDateTime(String paramString)
    {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");
        try
        {
            paramString = localSimpleDateFormat.format(f_format.parse(paramString));
            return paramString;
        }
        catch (ParseException paramString)
        {
            paramString.printStackTrace();
        }
        return "";
    }

    public static String getSimpleTime(String paramString)
    {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm");
        try
        {
            paramString = localSimpleDateFormat.format(f_format.parse(paramString));
            return paramString;
        }
        catch (ParseException paramString)
        {
            paramString.printStackTrace();
        }
        return "";
    }

    public static long getSpecialBeginTime(TextView paramTextView, long paramLong)
    {
        long l2 = System.currentTimeMillis() / 1000L;
        long l1 = paramLong;
        if (paramLong > l2) {
            l1 = l2;
        }
        paramTextView.setText(sk_time_s_long_2_str(l1));
        return l1;
    }

    public static long getSpecialEndTime(TextView paramTextView, long paramLong)
    {
        long l = System.currentTimeMillis() / 1000L;
        if ((paramLong == 0L) || (paramLong > l - 86400L))
        {
            paramTextView.setText(2131690129);
            return 0L;
        }
        paramTextView.setText(sk_time_s_long_2_str(paramLong));
        return paramLong;
    }

    public static String getTimeHM(String paramString)
    {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm");
        try
        {
            paramString = localSimpleDateFormat.format(f_format.parse(paramString));
            return paramString;
        }
        catch (ParseException paramString)
        {
            paramString.printStackTrace();
        }
        return "";
    }

    public static int getYear(String paramString)
    {
        try
        {
            int i = s_format.parse(paramString).getYear();
            return i + 1900;
        }
        catch (ParseException paramString)
        {
            paramString.printStackTrace();
        }
        return 0;
    }

    public static String long_to_yMdHm_str(long paramLong)
    {
        return sk_format_1.format(new Date(paramLong));
    }

    public static String s_long_2_str(long paramLong)
    {
        return s_format.format(new Date(paramLong));
    }

    public static long s_str_2_long(String paramString)
    {
        try
        {
            long l = s_format.parse(paramString).getTime();
            return l;
        }
        catch (ParseException paramString)
        {
            paramString.printStackTrace();
        }
        return 0L;
    }

    public static void saveOfflineTime()
    {
        long l = System.currentTimeMillis() / 1000L;
        XmppSpUtil localXmppSpUtil = XmppSpUtil.getInstanse();
        MjtApplication localMjtApplication = MjtApplication.getInstance();
        XmppSpUtil.getInstanse();
        localXmppSpUtil.putLong(localMjtApplication, "offline_time", l);
    }

    public static int sk_time_age(long paramLong)
    {
        int j = new Date().getYear() - new Date(1000L * paramLong).getYear();
        int i;
        if (j >= 0)
        {
            i = j;
            if (j <= 100) {}
        }
        else
        {
            i = 25;
        }
        return i;
    }

    public static int sk_time_current_time()
    {
        return (int)(System.currentTimeMillis() / 1000L);
    }

    public static long sk_time_current_time_ex()
    {
        return System.currentTimeMillis();
    }

    public static String sk_time_friendly_format2(long paramLong)
    {
        return friendly_format2.format(new Date(1000L * paramLong));
    }

    public static String sk_time_long_to_chat_time_str(long paramLong)
    {
        if (sk_time_s_long_2_str(paramLong).compareToIgnoreCase(sk_time_s_long_2_str(System.currentTimeMillis() / 1000L)) == 0) {
            return sk_time_long_to_hm_str(paramLong);
        }
        return long_to_yMdHm_str(paramLong * 1000L);
    }

    public static String sk_time_long_to_hm_str(long paramLong)
    {
        try
        {
            String str = hm_formater.format(new Date(1000L * paramLong));
            return str;
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
        }
        return "";
    }

    public static String sk_time_s_long_2_str(long paramLong)
    {
        return s_long_2_str(1000L * paramLong);
    }

    public static long sk_time_s_str_2_long(String paramString)
    {
        return s_str_2_long(paramString) / 1000L;
    }

    public static String sk_time_ss_long_2_str(long paramLong)
    {
        return ss_long_2_str(1000L * paramLong);
    }

    public static long sk_time_yMdHm_str_to_long(String paramString)
    {
        try
        {
            long l = sk_format_1.parse(paramString).getTime() / 1000L;
            return l;
        }
        catch (ParseException paramString)
        {
            paramString.printStackTrace();
        }
        return 0L;
    }

    public static int sms_time_current_time()
    {
        return (int)(System.currentTimeMillis() / 1000L);
    }

    public static String ss_long_2_str(long paramLong)
    {
        return ss_format.format(new Date(paramLong));
    }

    public static int yMdHm_getDayOfMonth(String paramString)
    {
        try
        {
            int i = sk_format_1.parse(paramString).getDate();
            return i;
        }
        catch (ParseException paramString)
        {
            paramString.printStackTrace();
        }
        return 0;
    }

    public static int yMdHm_getHours(String paramString)
    {
        try
        {
            int i = sk_format_1.parse(paramString).getHours();
            return i;
        }
        catch (ParseException paramString)
        {
            paramString.printStackTrace();
        }
        return 0;
    }

    public static int yMdHm_getMinutes(String paramString)
    {
        try
        {
            int i = sk_format_1.parse(paramString).getMinutes();
            return i;
        }
        catch (ParseException paramString)
        {
            paramString.printStackTrace();
        }
        return 0;
    }

    public static int yMdHm_getMonth(String paramString)
    {
        try
        {
            int i = sk_format_1.parse(paramString).getMonth();
            return i;
        }
        catch (ParseException paramString)
        {
            paramString.printStackTrace();
        }
        return 0;
    }

    public static int yMdHm_getYear(String paramString)
    {
        try
        {
            int i = sk_format_1.parse(paramString).getYear();
            return i + 1900;
        }
        catch (ParseException paramString)
        {
            paramString.printStackTrace();
        }
        return 0;
    }
}
