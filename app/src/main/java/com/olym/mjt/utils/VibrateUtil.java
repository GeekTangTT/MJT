package com.olym.mjt.utils;

import android.content.Context;
import android.os.Vibrator;

public class VibrateUtil
{
    public static void vibrate(Context paramContext, long paramLong)
    {
        ((Vibrator)paramContext.getApplicationContext().getSystemService("vibrator")).vibrate(paramLong);
    }

    public static void vibrate(Context paramContext, long[] paramArrayOfLong, int paramInt)
    {
        ((Vibrator)paramContext.getApplicationContext().getSystemService("vibrator")).vibrate(paramArrayOfLong, paramInt);
    }
}
