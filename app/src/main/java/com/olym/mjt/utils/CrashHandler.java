package com.olym.mjt.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Looper;
import android.os.Process;
import android.widget.Toast;
import com.lc.methodex.LogFinalUtils;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.module.others.SplashActivity;

public class CrashHandler
        implements Thread.UncaughtExceptionHandler
{
    private static CrashHandler mInstance;
    private MjtApplication context;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private CrashHandler(MjtApplication paramMjtApplication)
    {
        this.context = paramMjtApplication;
    }

    public static CrashHandler getInstance(MjtApplication paramMjtApplication)
    {
        try
        {
            if (mInstance == null) {
                mInstance = new CrashHandler(paramMjtApplication);
            }
            paramMjtApplication = mInstance;
            return paramMjtApplication;
        }
        finally {}
    }

    private boolean handleException(final Throwable paramThrowable)
    {
        new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    Looper.prepare();
                    Toast localToast = Toast.makeText(CrashHandler.this.context, "���������������������������������", 0);
                    localToast.setGravity(17, 0, 0);
                    localToast.show();
                    LogFinalUtils.logForException(paramThrowable);
                    Looper.loop();
                    return;
                }
                catch (Exception localException) {}
            }
        }).start();
        return paramThrowable != null;
    }

    private void restartApp()
    {
        try
        {
            Object localObject = new Intent(this.context.getApplicationContext(), SplashActivity.class);
            ((Intent)localObject).addCategory("android.intent.category.LAUNCHER");
            ((Intent)localObject).setFlags(268435456);
            ((Intent)localObject).setAction("android.intent.action.MAIN");
            localObject = PendingIntent.getActivity(this.context.getApplicationContext(), 0, (Intent)localObject, 134217728);
            ((AlarmManager)this.context.getSystemService("alarm")).set(1, System.currentTimeMillis() + 1000L, (PendingIntent)localObject);
            return;
        }
        catch (Exception localException) {}
    }

    public void init()
    {
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void uncaughtException(Thread paramThread, Throwable paramThrowable)
    {
        if ((!handleException(paramThrowable)) && (this.mDefaultHandler != null))
        {
            this.mDefaultHandler.uncaughtException(paramThread, paramThrowable);
            return;
        }
        try
        {
            Thread.sleep(1000L);
            Applog.systemOut("------handleException-----" + paramThrowable);
            restartApp();
            this.context.clearActivitys();
            Process.killProcess(Process.myPid());
            System.exit(1);
            System.gc();
            return;
        }
        catch (InterruptedException paramThread)
        {
            for (;;)
            {
                paramThread.printStackTrace();
            }
        }
    }
}

