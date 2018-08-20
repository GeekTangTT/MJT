package org.xutils.common.util;

import android.text.TextUtils;
import android.util.Log;
import org.xutils.x;

public class LogUtil
{
    public static String customTagPrefix = "x_log";

    public static void d(String paramString)
    {
        if (!x.isDebug()) {
            return;
        }
        Log.d(generateTag(), paramString);
    }

    public static void d(String paramString, Throwable paramThrowable)
    {
        if (!x.isDebug()) {
            return;
        }
        Log.d(generateTag(), paramString, paramThrowable);
    }

    public static void e(String paramString)
    {
        if (!x.isDebug()) {
            return;
        }
        Log.e(generateTag(), paramString);
    }

    public static void e(String paramString, Throwable paramThrowable)
    {
        if (!x.isDebug()) {
            return;
        }
        Log.e(generateTag(), paramString, paramThrowable);
    }

    private static String generateTag()
    {
        Object localObject = new Throwable().getStackTrace()[2];
        String str = ((StackTraceElement)localObject).getClassName();
        localObject = String.format("%s.%s(L:%d)", new Object[] { str.substring(str.lastIndexOf(".") + 1), ((StackTraceElement)localObject).getMethodName(), Integer.valueOf(((StackTraceElement)localObject).getLineNumber()) });
        if (TextUtils.isEmpty(customTagPrefix)) {
            return (String)localObject;
        }
        return customTagPrefix + ":" + (String)localObject;
    }

    public static void i(String paramString)
    {
        if (!x.isDebug()) {
            return;
        }
        Log.i(generateTag(), paramString);
    }

    public static void i(String paramString, Throwable paramThrowable)
    {
        if (!x.isDebug()) {
            return;
        }
        Log.i(generateTag(), paramString, paramThrowable);
    }

    public static void v(String paramString)
    {
        if (!x.isDebug()) {
            return;
        }
        Log.v(generateTag(), paramString);
    }

    public static void v(String paramString, Throwable paramThrowable)
    {
        if (!x.isDebug()) {
            return;
        }
        Log.v(generateTag(), paramString, paramThrowable);
    }

    public static void w(String paramString)
    {
        if (!x.isDebug()) {
            return;
        }
        Log.w(generateTag(), paramString);
    }

    public static void w(String paramString, Throwable paramThrowable)
    {
        if (!x.isDebug()) {
            return;
        }
        Log.w(generateTag(), paramString, paramThrowable);
    }

    public static void w(Throwable paramThrowable)
    {
        if (!x.isDebug()) {
            return;
        }
        Log.w(generateTag(), paramThrowable);
    }

    public static void wtf(String paramString)
    {
        if (!x.isDebug()) {
            return;
        }
        Log.wtf(generateTag(), paramString);
    }

    public static void wtf(String paramString, Throwable paramThrowable)
    {
        if (!x.isDebug()) {
            return;
        }
        Log.wtf(generateTag(), paramString, paramThrowable);
    }

    public static void wtf(Throwable paramThrowable)
    {
        if (!x.isDebug()) {
            return;
        }
        Log.wtf(generateTag(), paramThrowable);
    }
}

