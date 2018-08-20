package com.olym.mjt.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import com.lc.methodex.LogFinalUtils;
import com.olym.mjt.utils.sharedpreferencesutils.AppSpUtil;
import java.util.Locale;

public class DeviceInfoUtil
{
    public static String getDeviceId(Context paramContext)
    {
        if (AppSpUtil.getInstanse().getDeviceId() != null) {
            return AppSpUtil.getInstanse().getDeviceId();
        }
        Object localObject1 = (TelephonyManager)paramContext.getSystemService("phone");
        Object localObject2 = null;
        try
        {
            localObject1 = ((TelephonyManager)localObject1).getDeviceId();
            localObject2 = localObject1;
        }
        catch (Exception localException)
        {
            for (;;)
            {
                Applog.info("---getDeviceId--Exception-- " + localException);
            }
        }
        localObject1 = localObject2;
        if (localObject2 == null) {
            localObject1 = Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
        }
        paramContext = (Context)localObject1;
        if (localObject1 == null) {
            paramContext = "";
        }
        Applog.info_importance("-----------���������������--------- " + paramContext);
        AppSpUtil.getInstanse().setDeviceId(paramContext);
        return paramContext;
    }

    public static String getModel()
    {
        return Build.MODEL;
    }

    public static String getOsVersion()
    {
        return Build.VERSION.RELEASE;
    }

    public static int getVersionCode(Context paramContext)
    {
        PackageManager localPackageManager = paramContext.getPackageManager();
        try
        {
            int i = localPackageManager.getPackageInfo(paramContext.getPackageName(), 0).versionCode;
            return i;
        }
        catch (PackageManager.NameNotFoundException paramContext)
        {
            paramContext.printStackTrace();
            LogFinalUtils.logForException(paramContext);
        }
        return 0;
    }

    public static String getVersionName(Context paramContext)
    {
        PackageManager localPackageManager = paramContext.getPackageManager();
        try
        {
            paramContext = localPackageManager.getPackageInfo(paramContext.getPackageName(), 0).versionName;
            return paramContext;
        }
        catch (PackageManager.NameNotFoundException paramContext)
        {
            paramContext.printStackTrace();
            LogFinalUtils.logForException(paramContext);
        }
        return "";
    }

    public static boolean hasSimCard(Context paramContext)
    {
        switch (((TelephonyManager)paramContext.getSystemService("phone")).getSimState())
        {
            default:
                return true;
            case 1:
                return false;
        }
        return false;
    }

    public static boolean isZh(Context paramContext)
    {
        return paramContext.getResources().getConfiguration().locale.getLanguage().endsWith("zh");
    }
}

