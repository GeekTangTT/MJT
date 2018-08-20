package com.olym.mjt.utils.sharedpreferencesutils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceUtils
{
    private static SharedPreferences sp;

    public static boolean getBoolean(Context paramContext, String paramString)
    {
        return getBoolean(paramContext, paramString, false);
    }

    public static boolean getBoolean(Context paramContext, String paramString, boolean paramBoolean)
    {
        return getPreferences(paramContext).getBoolean(paramString, paramBoolean);
    }

    public static int getInt(Context paramContext, String paramString)
    {
        getPreferences(paramContext);
        return getInt(paramContext, paramString, -1);
    }

    public static int getInt(Context paramContext, String paramString, int paramInt)
    {
        return getPreferences(paramContext).getInt(paramString, paramInt);
    }

    public static Long getLong(Context paramContext, String paramString)
    {
        getPreferences(paramContext);
        return getLong(paramContext, paramString, 1L);
    }

    public static Long getLong(Context paramContext, String paramString, long paramLong)
    {
        return Long.valueOf(getPreferences(paramContext).getLong(paramString, paramLong));
    }

    private static SharedPreferences getPreferences(Context paramContext)
    {
        if (sp == null) {
            sp = paramContext.getSharedPreferences("config", 0);
        }
        return sp;
    }

    public static String getString(Context paramContext, String paramString)
    {
        getPreferences(paramContext);
        return getString(paramContext, paramString, null);
    }

    public static String getString(Context paramContext, String paramString1, String paramString2)
    {
        return getPreferences(paramContext).getString(paramString1, paramString2);
    }

    public static void putBoolean(Context paramContext, String paramString, boolean paramBoolean)
    {
        paramContext = getPreferences(paramContext).edit();
        paramContext.putBoolean(paramString, paramBoolean);
        paramContext.commit();
    }

    public static void putInt(Context paramContext, String paramString, int paramInt)
    {
        paramContext = getPreferences(paramContext).edit();
        paramContext.putInt(paramString, paramInt);
        paramContext.commit();
    }

    public static void putLong(Context paramContext, String paramString, long paramLong)
    {
        long l = paramLong;
        if (paramString.equals(Long.valueOf(XmppSpUtil.getInstanse().getOfflineTime())))
        {
            paramLong -= 30L;
            l = paramLong;
            if (paramLong <= 0L) {
                l = 0L;
            }
        }
        paramContext = getPreferences(paramContext).edit();
        paramContext.putLong(paramString, l);
        paramContext.commit();
    }

    public static void putString(Context paramContext, String paramString1, String paramString2)
    {
        paramContext = getPreferences(paramContext).edit();
        paramContext.putString(paramString1, paramString2);
        paramContext.commit();
    }
}

