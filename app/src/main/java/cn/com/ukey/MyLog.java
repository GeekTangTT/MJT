package cn.com.ukey;

import android.util.Log;

public final class MyLog
{
    private static boolean openLog = true;

    public static void d(String paramString1, String paramString2)
    {
        if (openLog) {
            Log.d(paramString1, paramString2);
        }
    }

    public static void e(String paramString1, String paramString2)
    {
        if (openLog) {
            Log.e(paramString1, paramString2);
        }
    }

    public static void i(String paramString1, String paramString2)
    {
        if (openLog) {
            Log.i(paramString1, paramString2);
        }
    }

    public static void w(String paramString1, String paramString2)
    {
        if (openLog) {
            Log.w(paramString1, paramString2);
        }
    }
}
