package com.olym.mjt.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.ApplicationInfo.DisplayNameComparator;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SafeActivityUtil
{
    public static final String TAG = "SafeActivityUtil";
    private static PackageManager pm;
    private static List<String> safePackages = new ArrayList();
    private List<ApplicationInfo> mlistAppInfo;

    public static boolean checkActivity(Context paramContext)
    {
        boolean bool2 = false;
        boolean bool1 = false;
        pm = paramContext.getPackageManager();
        Object localObject = pm.getInstalledApplications(8192);
        Collections.sort((List)localObject, new ApplicationInfo.DisplayNameComparator(pm));
        new ArrayList();
        localObject = ((List)localObject).iterator();
        while (((Iterator)localObject).hasNext())
        {
            ApplicationInfo localApplicationInfo = (ApplicationInfo)((Iterator)localObject).next();
            if ((localApplicationInfo.flags & 0x1) != 0) {
                safePackages.add(localApplicationInfo.packageName);
            }
        }
        localObject = (ActivityManager)paramContext.getSystemService("activity");
        try
        {
            i = Integer.valueOf(Build.VERSION.SDK).intValue();
            if (i >= 21)
            {
                localObject = getCurrentPkgName(paramContext);
                if (localObject == null) {
                    return bool2;
                }
                if (((String)localObject).equals(paramContext.getPackageName())) {
                    bool1 = true;
                }
                paramContext = safePackages.iterator();
                for (;;)
                {
                    bool2 = bool1;
                    if (!paramContext.hasNext()) {
                        break;
                    }
                    if (((String)paramContext.next()).equals(localObject)) {
                        bool1 = true;
                    }
                }
            }
        }
        catch (NumberFormatException localNumberFormatException)
        {
            for (;;)
            {
                int i = 0;
                continue;
                localObject = ((ActivityManager.RunningTaskInfo)((ActivityManager)localObject).getRunningTasks(1).get(0)).topActivity.getPackageName();
            }
        }
        return bool2;
    }

    public static void configSafePackages(List<String> paramList) {}

    public static String getCurrentPkgName(Context paramContext)
    {
        Object localObject3 = null;
        Object localObject1 = null;
        Object localObject2 = null;
        try
        {
            localObject4 = ActivityManager.RunningAppProcessInfo.class.getDeclaredField("processState");
            localObject1 = localObject4;
        }
        catch (Exception localException1)
        {
            for (;;)
            {
                Object localObject4;
                List localList;
                int i;
                localException1.printStackTrace();
                continue;
                i += 1;
            }
        }
        localList = ((ActivityManager)paramContext.getSystemService("activity")).getRunningAppProcesses();
        i = 0;
        paramContext = (Context)localObject3;
        if (i < localList.size())
        {
            localObject4 = (ActivityManager.RunningAppProcessInfo)localList.get(i);
            if (((ActivityManager.RunningAppProcessInfo)localObject4).importance == 100) {
                paramContext = null;
            }
        }
        else
        {
            try
            {
                int j = ((Field)localObject1).getInt(localObject4);
                paramContext = Integer.valueOf(j);
            }
            catch (Exception localException2)
            {
                for (;;)
                {
                    localException2.printStackTrace();
                }
            }
            if ((paramContext != null) && (paramContext.intValue() == 2))
            {
                paramContext = (Context)localObject4;
                localObject1 = localObject2;
                if (paramContext != null) {
                    localObject1 = paramContext.processName;
                }
                return (String)localObject1;
            }
        }
    }
}
