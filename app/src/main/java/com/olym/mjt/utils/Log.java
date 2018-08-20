package com.olym.mjt.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log
{
    private static int logLevel = 1;

    public static String _FILE_()
    {
        return new Exception().getStackTrace()[1].getFileName();
    }

    public static String _FUNC_()
    {
        return new Exception().getStackTrace()[1].getMethodName();
    }

    public static int _LINE_()
    {
        return new Exception().getStackTrace()[1].getLineNumber();
    }

    public static String _TIME_()
    {
        Date localDate = new Date();
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(localDate);
    }

    public static void d(String paramString1, String paramString2)
    {
        if (logLevel >= 4) {
            android.util.Log.d(paramString1, paramString2);
        }
    }

    public static void d(String paramString1, String paramString2, Throwable paramThrowable)
    {
        if (logLevel >= 4) {
            android.util.Log.d(paramString1, paramString2, paramThrowable);
        }
    }

    public static void e(String paramString1, String paramString2)
    {
        if (logLevel >= 1) {
            android.util.Log.e(paramString1, paramString2);
        }
    }

    public static void e(String paramString1, String paramString2, Throwable paramThrowable)
    {
        if (logLevel >= 1) {
            android.util.Log.e(paramString1, paramString2, paramThrowable);
        }
    }

    public static int getLogLevel()
    {
        return logLevel;
    }

    public static void i(String paramString1, String paramString2)
    {
        if (logLevel >= 3) {
            android.util.Log.i(paramString1, paramString2);
        }
    }

    static void i(String paramString1, String paramString2, Throwable paramThrowable)
    {
        if (logLevel >= 3) {
            android.util.Log.i(paramString1, paramString2, paramThrowable);
        }
    }

    public static String location()
    {
        StackTraceElement localStackTraceElement = new Exception().getStackTrace()[2];
        return localStackTraceElement.getFileName() + ":" + String.valueOf(new StringBuilder().append(localStackTraceElement.getLineNumber()).append(" ").append(localStackTraceElement.getMethodName()).toString());
    }

    public static void setLogLevel(int paramInt)
    {
        logLevel = paramInt;
    }

    public static void v(String paramString1, String paramString2)
    {
        if (logLevel >= 5) {
            android.util.Log.v(paramString1, paramString2);
        }
    }

    public static void v(String paramString1, String paramString2, Throwable paramThrowable)
    {
        if (logLevel >= 5) {
            android.util.Log.v(paramString1, paramString2, paramThrowable);
        }
    }

    public static void w(String paramString1, String paramString2)
    {
        if (logLevel >= 2) {
            android.util.Log.w(paramString1, paramString2);
        }
    }

    public static void w(String paramString1, String paramString2, Throwable paramThrowable)
    {
        if (logLevel >= 2) {
            android.util.Log.w(paramString1, paramString2, paramThrowable);
        }
    }

    public static void z(String paramString)
    {
        android.util.Log.d("zhoujava " + _TIME_() + " " + location(), paramString);
    }
}

