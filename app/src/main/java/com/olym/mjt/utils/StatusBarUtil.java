package com.olym.mjt.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.lc.methodex.LogFinalUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import qiu.niorgai.StatusBarCompat;

public class StatusBarUtil
{
    private static void applay(Activity paramActivity, boolean paramBoolean)
    {
        if (Build.VERSION.SDK_INT >= 19)
        {
            if (!RomUtil.isFlyme()) {
                break label34;
            }
            StatusBarCompat.setStatusBarColor(paramActivity, paramActivity.getResources().getColor(2131034250));
            setStatusBarLightModeFlyme(paramActivity, paramBoolean);
        }
        label34:
        do
        {
            return;
            if (RomUtil.isMiui())
            {
                StatusBarCompat.setStatusBarColor(paramActivity, paramActivity.getResources().getColor(2131034250));
                setStatusBarLightModeMIUI(paramActivity, paramBoolean);
                return;
            }
        } while (Build.VERSION.SDK_INT < 23);
        StatusBarCompat.setStatusBarColor(paramActivity, paramActivity.getResources().getColor(2131034250));
        setStatusBarLightModeOSM(paramActivity, paramBoolean);
    }

    public static void setStatusBar(Activity paramActivity)
    {
        applay(paramActivity, true);
    }

    private static boolean setStatusBarLightModeFlyme(Activity paramActivity, boolean paramBoolean)
    {
        paramActivity = paramActivity.getWindow();
        boolean bool = false;
        if (paramActivity != null) {}
        try
        {
            WindowManager.LayoutParams localLayoutParams = paramActivity.getAttributes();
            Field localField1 = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field localField2 = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            localField1.setAccessible(true);
            localField2.setAccessible(true);
            int i = localField1.getInt(null);
            int j = localField2.getInt(localLayoutParams);
            if (paramBoolean) {}
            for (i = j | i;; i = j & (i ^ 0xFFFFFFFF))
            {
                localField2.setInt(localLayoutParams, i);
                paramActivity.setAttributes(localLayoutParams);
                bool = true;
                return bool;
            }
            return false;
        }
        catch (Exception paramActivity)
        {
            try
            {
                LogFinalUtils.logForException(paramActivity);
                return false;
            }
            catch (Exception paramActivity)
            {
                LogFinalUtils.logForException(paramActivity);
            }
        }
    }

    private static boolean setStatusBarLightModeMIUI(Activity paramActivity, boolean paramBoolean)
    {
        paramActivity = paramActivity.getWindow();
        boolean bool = false;
        if (paramActivity != null) {
            try
            {
                Object localObject = paramActivity.getClass();
                try
                {
                    Class localClass = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                    int i = localClass.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(localClass);
                    localObject = ((Class)localObject).getMethod("setExtraFlags", new Class[] { Integer.TYPE, Integer.TYPE });
                    if (paramBoolean) {
                        ((Method)localObject).invoke(paramActivity, new Object[] { Integer.valueOf(i), Integer.valueOf(i) });
                    } else {
                        ((Method)localObject).invoke(paramActivity, new Object[] { Integer.valueOf(0), Integer.valueOf(i) });
                    }
                }
                catch (Exception paramActivity)
                {
                    LogFinalUtils.logForException(paramActivity);
                    return false;
                }
                bool = true;
            }
            catch (Exception paramActivity)
            {
                LogFinalUtils.logForException(paramActivity);
                return false;
            }
        }
        return bool;
    }

    private static boolean setStatusBarLightModeOSM(Activity paramActivity, boolean paramBoolean)
    {
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (paramBoolean) {
                paramActivity.getWindow().getDecorView().setSystemUiVisibility(8192);
            }
            for (;;)
            {
                return true;
                int i = paramActivity.getWindow().getDecorView().getSystemUiVisibility();
                paramActivity.getWindow().getDecorView().setSystemUiVisibility(i | 0x0);
            }
        }
        return false;
    }
}
