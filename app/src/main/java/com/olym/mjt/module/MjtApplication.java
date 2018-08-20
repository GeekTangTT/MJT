package com.olym.mjt.module;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlarmManager;
import android.app.Application.ActivityLifecycleCallbacks;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.support.annotation.RequiresApi;
import android.support.multidex.MultiDexApplication;
import com.lc.methodex.LogFinalUtils;
import com.olym.mjt.databean.bean.ServiceInfo;
import com.olym.mjt.databean.bean.User;
import com.olym.mjt.databean.event.ExceptionLogoutEvent;
import com.olym.mjt.datastat.UploadDataUtils;
import com.olym.mjt.module.message.manager.ContactRefreshDoneListener;
import com.olym.mjt.module.others.SplashActivity;
import com.olym.mjt.pjsip.sip.api.SipCallSession;
import com.olym.mjt.pjsip.sip.api.SipProfile;
import com.olym.mjt.pjsip.sip.service.SipService;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.utils.DeviceInfoUtil;
import com.olym.mjt.utils.Md5Util;
import com.olym.mjt.utils.SelfDistructCheck;
import com.olym.mjt.utils.sharedpreferencesutils.AppSpUtil;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;
import com.olym.mjt.utils.siputils.SipAccountUtils;
import com.olym.mjt.widget.applock.LockPatternUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.CrashReport.CrashHandleCallback;
import com.tencent.bugly.crashreport.CrashReport.UserStrategy;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.xutils.x.Ext;
import xiaofei.library.hermeseventbus.HermesEventBus;

public class MjtApplication
        extends MultiDexApplication
{
    private static MjtApplication INSTANCE = null;
    public static final int appLogLevel = 10;
    public static final boolean companyContacts = false;
    private static Map<String, Activity> destoryMap = new HashMap();
    public static final boolean isOpenAppLog1 = true;
    public static final boolean isOpenAppLog2 = false;
    public static final boolean isOpenAppSysOut = false;
    public static final boolean isOpenEngineLog = false;
    public static final boolean isOpenPjsipLog = true;
    public static boolean isP2POpen = false;
    public static final boolean isV3 = false;
    public static final boolean sipEncrypt = true;
    private String TF_path;
    private String accessToken;
    public ArrayList<Activity> activities = new ArrayList();
    private int appCount = 0;
    public String callNumber = null;
    public long callTime;
    private HashSet<ContactRefreshDoneListener> contactRefreshListenerList = new HashSet();
    private boolean gotoForegroundForShare = false;
    private long gotoSystemAppTime = 0L;
    private String groupPrivateKey;
    public boolean isAppNormal = false;
    private boolean isCheckdGesture = false;
    private boolean isEnforce2G = false;
    private boolean isHasCallView = false;
    private boolean isIncomingCall = false;
    private boolean isLockedScreen = false;
    private boolean isShouldStartGesture = false;
    private boolean isSipRegisted = false;
    private boolean isStartFromBoot;
    public long lastPushTime = 0L;
    private User loginUser;
    public String mAppDir;
    public String mEncryptFilesDir;
    public String mFilesDir;
    public String mGesturePassNewName;
    public String mGesturePassOldName;
    public String mLogDir;
    public String mOtherGesturePassNewName;
    public String mOtherGesturePassOldName;
    public String mPicturesDir;
    public String mTempFile;
    public String mVideosDir;
    public String mVoicesDir;
    private SipCallSession mainSipCallSession;
    public boolean shouldCloseCallPCM = false;
    private SipProfile sipProfile;

    public static void addDestoryActivity(Activity paramActivity, String paramString)
    {
        destoryMap.put(paramString, paramActivity);
    }

    public static void destoryActivity(String paramString)
    {
        paramString = destoryMap.keySet().iterator();
        while (paramString.hasNext())
        {
            String str = (String)paramString.next();
            ((Activity)destoryMap.get(str)).finish();
        }
    }

    public static MjtApplication getInstance()
    {
        return INSTANCE;
    }

    private void initBugly()
    {
        CrashReport.UserStrategy localUserStrategy = new CrashReport.UserStrategy(this);
        localUserStrategy.setAppChannel(ChannelUtil.currentChannel + "");
        localUserStrategy.setCrashHandleCallback(new CrashReport.CrashHandleCallback()
        {
            @RequiresApi(api=17)
            public Map<String, String> onCrashHandleStart(int paramAnonymousInt, String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3)
            {
                paramAnonymousString1 = "----������������---crashType:" + paramAnonymousInt + " errorType:" + paramAnonymousString1 + " errorMessage:" + paramAnonymousString2 + " errorStack:" + paramAnonymousString3;
                Applog.systemOut(paramAnonymousString1);
                Applog.info(paramAnonymousString1);
                UploadDataUtils.uploadCrashEvent(paramAnonymousString1);
                new Thread(new MjtApplication.2.1(this)).start();
                try
                {
                    Thread.sleep(800L);
                    new Thread(new MjtApplication.2.2(this)).start();
                    paramAnonymousString1 = new HashMap();
                    if (MjtApplication.this.loginUser != null) {
                        paramAnonymousString1.put("phone", MjtApplication.this.loginUser.getTelephone());
                    }
                    return paramAnonymousString1;
                }
                catch (InterruptedException paramAnonymousString1)
                {
                    for (;;)
                    {
                        paramAnonymousString1.printStackTrace();
                    }
                }
            }
        });
        CrashReport.initCrashReport(getApplicationContext(), "4b90b3bb06", false, localUserStrategy);
    }

    private void initLog()
    {
        int i = Process.myPid();
        String str = "";
        Iterator localIterator = ((ActivityManager)getSystemService("activity")).getRunningAppProcesses().iterator();
        do
        {
            localObject = str;
            if (!localIterator.hasNext()) {
                break;
            }
            localObject = (ActivityManager.RunningAppProcessInfo)localIterator.next();
        } while (((ActivityManager.RunningAppProcessInfo)localObject).pid != i);
        Object localObject = ((ActivityManager.RunningAppProcessInfo)localObject).processName;
        if (((String)localObject).equals(getPackageName())) {
            if (!"mounted".equals(Environment.getExternalStorageState())) {
                break label177;
            }
        }
        for (localObject = getExternalFilesDir(null).getParent();; localObject = getFilesDir().getParent())
        {
            localObject = new File((String)localObject + "/logs");
            if (!((File)localObject).exists()) {
                ((File)localObject).mkdirs();
            }
            this.mLogDir = ((File)localObject).getAbsolutePath();
            Applog.systemOut("-------mLogDir----- " + this.mLogDir);
            label177:
            try
            {
                LogFinalUtils.init(false, ((File)localObject).getAbsolutePath());
                return;
            }
            catch (Exception localException) {}
        }
    }

    public static void removeActivity(Activity paramActivity, String paramString)
    {
        paramActivity = destoryMap.keySet().iterator();
        while (paramActivity.hasNext())
        {
            paramString = (String)paramActivity.next();
            destoryMap.remove(paramString);
        }
    }

    private void restartApp()
    {
        try
        {
            Object localObject = new Intent(this, SplashActivity.class);
            ((Intent)localObject).addCategory("android.intent.category.LAUNCHER");
            ((Intent)localObject).setFlags(268435456);
            ((Intent)localObject).setAction("android.intent.action.MAIN");
            localObject = PendingIntent.getActivity(this, 0, (Intent)localObject, 134217728);
            ((AlarmManager)getSystemService("alarm")).set(1, System.currentTimeMillis() + 1000L, (PendingIntent)localObject);
            return;
        }
        catch (Exception localException) {}
    }

    private void startPasswordCheckActivity(Activity paramActivity)
    {
        if (ChannelUtil.currentChannel == 102) {
            this.gotoSystemAppTime = 0L;
        }
        if ((ChannelUtil.action_gesture) && (this.loginUser != null) && (!this.loginUser.isErrorUser()) && (!this.isIncomingCall) && (!this.gotoForegroundForShare) && (this.mainSipCallSession == null) && (!this.isCheckdGesture)) {
            if ((this.gotoSystemAppTime == 0L) || (System.currentTimeMillis() - this.gotoSystemAppTime >= 180000L)) {}
        }
        for (;;)
        {
            this.isIncomingCall = false;
            this.gotoSystemAppTime = 0L;
            this.gotoForegroundForShare = false;
            return;
            if (paramActivity != null)
            {
                LockPatternUtils.gotoCheckView(paramActivity);
            }
            else
            {
                LockPatternUtils.gotoCheckViewFromNoActivity(this);
                continue;
                if (this.mainSipCallSession != null)
                {
                    paramActivity = PendingIntent.getActivity(this, 0, SipService.buildCallUiIntentFromNotification(this), 0);
                    try
                    {
                        paramActivity.send();
                    }
                    catch (PendingIntent.CanceledException paramActivity)
                    {
                        paramActivity.printStackTrace();
                        LogFinalUtils.logForException(paramActivity);
                    }
                }
            }
        }
    }

    public void addContactRefreshListenerList(ContactRefreshDoneListener paramContactRefreshDoneListener)
    {
        this.contactRefreshListenerList.add(paramContactRefreshDoneListener);
    }

    @RequiresApi(api=17)
    public void clearActivitys()
    {
        synchronized (this.activities)
        {
            Iterator localIterator = this.activities.iterator();
            while (localIterator.hasNext())
            {
                Activity localActivity = (Activity)localIterator.next();
                if ((localActivity != null) && (!localActivity.isDestroyed())) {
                    localActivity.finish();
                }
            }
        }
    }

    public void clearDatas()
    {
        this.accessToken = null;
        this.loginUser = null;
    }

    public String getAccessToken()
    {
        if (this.accessToken == null) {
            this.accessToken = UserSpUtil.getInstanse().getAccessToken();
        }
        return this.accessToken;
    }

    public HashSet<ContactRefreshDoneListener> getContactRefreshListenerList()
    {
        return this.contactRefreshListenerList;
    }

    public long getGotoSystemAppTime()
    {
        return this.gotoSystemAppTime;
    }

    public String getGroupPrivateKey()
    {
        String str = getLoginUser().getTelephone();
        if ((this.groupPrivateKey == null) && (str != null)) {
            this.groupPrivateKey = Md5Util.toMD5(str + DeviceInfoUtil.getDeviceId(this));
        }
        return this.groupPrivateKey;
    }

    public Activity getLastActivity()
    {
        int i = this.activities.size();
        if (i > 0) {
            return (Activity)this.activities.get(i - 1);
        }
        return null;
    }

    public User getLoginUser()
    {
        if (this.loginUser == null) {
            this.loginUser = UserSpUtil.getInstanse().getLoginUser();
        }
        return this.loginUser;
    }

    public SipCallSession getMainSipCallSession()
    {
        return this.mainSipCallSession;
    }

    public SipProfile getSipProfile()
    {
        if (this.sipProfile == null) {
            SipAccountUtils.checkAccount(this, UserSpUtil.getInstanse().getPhone(), UserSpUtil.getInstanse().getPassword(), UserSpUtil.getInstanse().getIBCDomain());
        }
        Applog.systemOut("------getSipProfile---- " + this.sipProfile.reg_uri);
        return this.sipProfile;
    }

    public String getTF_path()
    {
        return this.TF_path;
    }

    public void initAppDir()
    {
        File localFile;
        if ("mounted".equals(Environment.getExternalStorageState()))
        {
            localFile = getExternalFilesDir(null);
            if (localFile == null) {
                this.mAppDir = getFilesDir().getAbsolutePath();
            }
        }
        for (;;)
        {
            localFile = new File(this.mAppDir + File.separator + "Pictures");
            if (!localFile.exists()) {
                localFile.mkdirs();
            }
            this.mPicturesDir = localFile.getAbsolutePath();
            localFile = new File(this.mAppDir + File.separator + "Music");
            if (!localFile.exists()) {
                localFile.mkdirs();
            }
            this.mVoicesDir = localFile.getAbsolutePath();
            localFile = new File(this.mAppDir + File.separator + "Movies");
            if (!localFile.exists()) {
                localFile.mkdirs();
            }
            this.mVideosDir = localFile.getAbsolutePath();
            localFile = new File(this.mAppDir + File.separator + "TempFile");
            if (!localFile.exists()) {
                localFile.mkdirs();
            }
            this.mTempFile = localFile.getAbsolutePath();
            localFile = new File(this.mAppDir + File.separator + "Download");
            if (!localFile.exists()) {
                localFile.mkdirs();
            }
            this.mFilesDir = localFile.getAbsolutePath();
            localFile = new File(this.mAppDir + File.separator + "tempEncryptFile");
            if (!localFile.exists()) {
                localFile.mkdirs();
            }
            this.mEncryptFilesDir = localFile.getAbsolutePath();
            Applog.systemOut("------mEncryptFilesDir--- " + this.mEncryptFilesDir);
            localFile = getFilesDir();
            if (!localFile.exists()) {
                localFile.mkdirs();
            }
            this.mGesturePassOldName = (localFile.getAbsolutePath() + File.separator + "gestureold");
            this.mGesturePassNewName = (localFile.getAbsolutePath() + File.separator + "gesturenew");
            this.mOtherGesturePassOldName = (localFile.getAbsolutePath() + File.separator + "othergestureold");
            this.mOtherGesturePassNewName = (localFile.getAbsolutePath() + File.separator + "othergesturenew");
            return;
            this.mAppDir = localFile.getAbsolutePath();
            continue;
            this.mAppDir = getFilesDir().getAbsolutePath();
        }
    }

    public boolean isAppBackground()
    {
        return this.appCount == 0;
    }

    public boolean isAppForeground()
    {
        return this.appCount > 0;
    }

    public boolean isCheckdGesture()
    {
        return this.isCheckdGesture;
    }

    public boolean isEnforce2G()
    {
        return this.isEnforce2G;
    }

    public boolean isGotoForegroundForShare()
    {
        return this.gotoForegroundForShare;
    }

    public boolean isHasCallView()
    {
        return this.isHasCallView;
    }

    public boolean isIncomingCall()
    {
        return this.isIncomingCall;
    }

    public boolean isLockedScreen()
    {
        return this.isLockedScreen;
    }

    public boolean isShouldStartGesture()
    {
        return this.isShouldStartGesture;
    }

    public boolean isSipRegisted()
    {
        return this.isSipRegisted;
    }

    public boolean isStartFromBoot()
    {
        return this.isStartFromBoot;
    }

    public void onCreate()
    {
        boolean bool = true;
        super.onCreate();
        ChannelUtil.setChannel(100);
        initLog();
        INSTANCE = this;
        ServiceInfo localServiceInfo = UserSpUtil.getInstanse().getServiceInfo();
        if (localServiceInfo != null) {
            if (localServiceInfo.getP2p_status() != 1) {
                break label184;
            }
        }
        for (;;)
        {
            isP2POpen = bool;
            x.Ext.init(this);
            HermesEventBus.getDefault().init(this);
            initBugly();
            System.setProperty("com.j256.ormlite.logger.level", "ERROR");
            initAppDir();
            Applog.info("-----AppStart------" + Build.MODEL + "-----" + DeviceInfoUtil.getVersionName(this) + "-----" + ChannelUtil.currentChannel);
            Applog.systemOut("-----AppStart------" + Build.MODEL + "-----" + DeviceInfoUtil.getVersionName(this) + "-----" + ChannelUtil.currentChannel);
            registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks()
            {
                public void onActivityCreated(Activity paramAnonymousActivity, Bundle arg2)
                {
                    Applog.info("-----onActivityCreated---" + paramAnonymousActivity);
                    Applog.systemOut("-----onActivityCreated---" + paramAnonymousActivity);
                    synchronized (MjtApplication.this.activities)
                    {
                        MjtApplication.this.activities.add(paramAnonymousActivity);
                        return;
                    }
                }

                public void onActivityDestroyed(Activity paramAnonymousActivity)
                {
                    Applog.systemOut("-----onActivityDestroyed---" + paramAnonymousActivity);
                    Applog.info("-----onActivityDestroyed---" + paramAnonymousActivity);
                    if (paramAnonymousActivity.getComponentName().getClassName().contains("InCallActivity")) {
                        MjtApplication.access$002(MjtApplication.this, false);
                    }
                    synchronized (MjtApplication.this.activities)
                    {
                        MjtApplication.this.activities.remove(paramAnonymousActivity);
                        return;
                    }
                }

                public void onActivityPaused(Activity paramAnonymousActivity)
                {
                    Applog.info("-----onActivityPaused---" + paramAnonymousActivity);
                    Applog.systemOut("-----onActivityPaused---" + paramAnonymousActivity);
                }

                public void onActivityResumed(Activity paramAnonymousActivity)
                {
                    Applog.info("-----onActivityResumed---" + paramAnonymousActivity);
                    Applog.systemOut("-----onActivityResumed---" + paramAnonymousActivity);
                }

                public void onActivitySaveInstanceState(Activity paramAnonymousActivity, Bundle paramAnonymousBundle) {}

                public void onActivityStarted(Activity paramAnonymousActivity)
                {
                    Applog.systemOut("-----onActivityStarted---- " + paramAnonymousActivity);
                    if (paramAnonymousActivity.getComponentName().getClassName().contains("InCallActivity")) {
                        MjtApplication.access$002(MjtApplication.this, true);
                    }
                    MjtApplication.access$108(MjtApplication.this);
                    if ((!paramAnonymousActivity.getComponentName().getClassName().contains("SplashActivity")) && (!paramAnonymousActivity.getComponentName().getClassName().contains("FloatActivity")) && (!paramAnonymousActivity.getComponentName().getClassName().contains("TranslucentActivity")) && (MjtApplication.this.appCount == 1))
                    {
                        Applog.systemOut("------APP������������-----");
                        if (!paramAnonymousActivity.getComponentName().getClassName().contains("GestureActivity")) {
                            MjtApplication.this.startPasswordCheckActivity(paramAnonymousActivity);
                        }
                        if (SelfDistructCheck.check()) {
                            ExceptionLogoutEvent.post(new ExceptionLogoutEvent("key_self_destruct"));
                        }
                    }
                }

                public void onActivityStopped(Activity paramAnonymousActivity)
                {
                    MjtApplication.access$110(MjtApplication.this);
                    if (MjtApplication.this.appCount == 0)
                    {
                        Applog.systemOut("------APP������������-----");
                        AppSpUtil.getInstanse().setBackgroundTime(System.currentTimeMillis());
                    }
                }
            });
            return;
            label184:
            bool = false;
        }
    }

    public void onTrimMemory(int paramInt)
    {
        super.onTrimMemory(paramInt);
        Applog.info("----onTrimMemory----- " + Process.myPid() + "   " + paramInt);
    }

    public void setAccessToken(String paramString)
    {
        this.accessToken = paramString;
    }

    public void setCheckdGesture(boolean paramBoolean)
    {
        this.isCheckdGesture = paramBoolean;
    }

    public void setEnforce2G(boolean paramBoolean)
    {
        this.isEnforce2G = paramBoolean;
    }

    public void setGotoForegroundForShare(boolean paramBoolean)
    {
        this.gotoForegroundForShare = paramBoolean;
    }

    public void setGotoSystemAppTime(long paramLong)
    {
        this.gotoSystemAppTime = paramLong;
    }

    public void setIncomingCall(boolean paramBoolean)
    {
        this.isIncomingCall = paramBoolean;
    }

    public void setLockedScreen(boolean paramBoolean)
    {
        this.isLockedScreen = paramBoolean;
    }

    public void setLoginUser(User paramUser)
    {
        this.loginUser = paramUser;
    }

    public void setMainSipCallSession(SipCallSession paramSipCallSession)
    {
        this.mainSipCallSession = paramSipCallSession;
    }

    public void setShouldStartGesture(boolean paramBoolean)
    {
        this.isShouldStartGesture = paramBoolean;
    }

    public void setSipProfile(SipProfile paramSipProfile)
    {
        this.sipProfile = paramSipProfile;
        Applog.systemOut("------getSipProfile---- " + paramSipProfile.reg_uri);
    }

    public void setSipRegisted(boolean paramBoolean)
    {
        this.isSipRegisted = paramBoolean;
    }

    public void setStartFromBoot(boolean paramBoolean)
    {
        this.isStartFromBoot = paramBoolean;
    }

    public void setTF_path(String paramString)
    {
        this.TF_path = paramString;
    }
}

