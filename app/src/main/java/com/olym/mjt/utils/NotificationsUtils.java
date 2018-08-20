package com.olym.mjt.utils;

import android.app.AppOpsManager;
import android.app.Notification;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import com.lc.methodex.LogFinalUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class NotificationsUtils
{
    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final double COLOR_THRESHOLD = 180.0D;
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
    private static final String TAG = NotificationsUtils.class.getSimpleName();
    private static int titleColor;

    private static int getNotificationColor(Context paramContext)
    {
        if ((paramContext instanceof AppCompatActivity)) {
            return getNotificationColorCompat(paramContext);
        }
        return getNotificationColorInternal(paramContext);
    }

    private static int getNotificationColorCompat(Context paramContext)
    {
        int i = new NotificationCompat.Builder(paramContext).build().contentView.getLayoutId();
        paramContext = (ViewGroup)LayoutInflater.from(paramContext).inflate(i, null);
        Object localObject = (TextView)paramContext.findViewById(16908310);
        if (localObject == null)
        {
            localObject = new ArrayList();
            iteratoryView(paramContext, new Filter()
            {
                public void filter(View paramAnonymousView)
                {
                    this.val$textViews.add((TextView)paramAnonymousView);
                }
            });
            float f1 = -2.14748365E9F;
            int j = 0;
            i = 0;
            int k = ((List)localObject).size();
            while (i < k)
            {
                float f3 = ((TextView)((List)localObject).get(i)).getTextSize();
                float f2 = f1;
                if (f3 > f1)
                {
                    f2 = f3;
                    j = i;
                }
                i += 1;
                f1 = f2;
            }
            return ((TextView)((List)localObject).get(j)).getCurrentTextColor();
        }
        return ((TextView)localObject).getCurrentTextColor();
    }

    private static int getNotificationColorInternal(Context paramContext)
    {
        Object localObject = new NotificationCompat.Builder(paramContext);
        ((NotificationCompat.Builder)localObject).setContentText("DUMMY_TITLE");
        paramContext = (ViewGroup)((NotificationCompat.Builder)localObject).build().contentView.apply(paramContext, new FrameLayout(paramContext));
        localObject = (TextView)paramContext.findViewById(16908310);
        if (localObject == null)
        {
            iteratoryView(paramContext, new Filter()
            {
                public void filter(View paramAnonymousView)
                {
                    if ((paramAnonymousView instanceof TextView))
                    {
                        paramAnonymousView = (TextView)paramAnonymousView;
                        if ("DUMMY_TITLE".equals(paramAnonymousView.getText().toString())) {
                            NotificationsUtils.access$002(paramAnonymousView.getCurrentTextColor());
                        }
                    }
                }
            });
            return titleColor;
        }
        return ((TextView)localObject).getCurrentTextColor();
    }

    private static boolean isColorSimilar(int paramInt1, int paramInt2)
    {
        paramInt1 |= 0xFF000000;
        int j = paramInt2 | 0xFF000000;
        paramInt2 = Color.red(paramInt1) - Color.red(j);
        int i = Color.green(paramInt1) - Color.green(j);
        paramInt1 = Color.blue(paramInt1) - Color.blue(j);
        return Math.sqrt(paramInt2 * paramInt2 + i * i + paramInt1 * paramInt1) < 180.0D;
    }

    public static boolean isDarkNotiFicationBar(Context paramContext)
    {
        return !isColorSimilar(-16777216, getNotificationColor(paramContext));
    }

    public static boolean isNotificationEnabled(Context paramContext)
    {
        try
        {
            if (Build.VERSION.SDK_INT >= 19)
            {
                AppOpsManager localAppOpsManager = (AppOpsManager)paramContext.getSystemService("appops");
                Object localObject = paramContext.getApplicationInfo();
                paramContext = paramContext.getApplicationContext().getPackageName();
                int i = ((ApplicationInfo)localObject).uid;
                localObject = Class.forName(AppOpsManager.class.getName());
                i = ((Integer)((Class)localObject).getMethod("checkOpNoThrow", new Class[] { Integer.TYPE, Integer.TYPE, String.class }).invoke(localAppOpsManager, new Object[] { Integer.valueOf(((Integer)((Class)localObject).getDeclaredField("OP_POST_NOTIFICATION").get(Integer.class)).intValue()), Integer.valueOf(i), paramContext })).intValue();
                return i == 0;
            }
        }
        catch (Exception paramContext)
        {
            LogFinalUtils.logForException(paramContext);
        }
        return true;
    }

    private static void iteratoryView(View paramView, Filter paramFilter)
    {
        if ((paramView == null) || (paramFilter == null)) {}
        for (;;)
        {
            return;
            paramFilter.filter(paramView);
            if ((paramView instanceof ViewGroup))
            {
                paramView = (ViewGroup)paramView;
                int i = 0;
                int j = paramView.getChildCount();
                while (i < j)
                {
                    iteratoryView(paramView.getChildAt(i), paramFilter);
                    i += 1;
                }
            }
        }
    }

    private static abstract interface Filter
    {
        public abstract void filter(View paramView);
    }
}
