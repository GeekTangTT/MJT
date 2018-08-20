package com.olym.mjt.module.others;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.lc.methodex.LogFinalUtils;
import com.nisc.SecurityEngine;
import com.nisc.UKeyManageInterface;
import com.nisc.api.SecEngineException;
import com.olym.mjt.base.activity.ForbidScreenShotActivity;
import com.olym.mjt.bracelet.ConfigNewBraceletService;
import com.olym.mjt.bracelet.ConnectOldBraceletService;
import com.olym.mjt.bracelet.event.BraceletConnectSuccessEvent;
import com.olym.mjt.bracelet.event.BraceletFinishEvent;
import com.olym.mjt.config.AppConfig;
import com.olym.mjt.database.DBManager;
import com.olym.mjt.databean.bean.User;
import com.olym.mjt.init.EngineUtils;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.module.main.MJTMainActivity;
import com.olym.mjt.module.notification.NotificationService;
import com.olym.mjt.pjsip.sip.api.SipManager;
import com.olym.mjt.pjsip.sip.api.SipProfile;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.utils.DeviceInfoUtil;
import com.olym.mjt.utils.EventBusUtil;
import com.olym.mjt.utils.LoginHelper;
import com.olym.mjt.utils.SelfDistructCheck;
import com.olym.mjt.utils.ToastUtils;
import com.olym.mjt.utils.ViewTransferUtil;
import com.olym.mjt.utils.sharedpreferencesutils.AppSpUtil;
import com.olym.mjt.utils.sharedpreferencesutils.IBCSpUtil;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;
import com.olym.mjt.widget.applock.LockPatternUtils;
import com.olym.mjt.widget.dialog.LoadingDialog;
import com.olym.mjt.widget.dialog.TipsDialog;
import com.olym.mjt.widget.dialog.TipsDialog.Build;
import java.util.ArrayList;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SplashActivity
        extends ForbidScreenShotActivity
{
    private boolean jumpToMain = false;
    private LoadingDialog loadingDialog;
    private String password;
    private Intent shareintent;
    private TipsDialog tipsDialog;
    private String userPhone = null;

    private boolean checkPrivateKeyStatus()
    {
        boolean bool2 = false;
        try
        {
            if (!NotificationService.isStarted)
            {
                SecurityEngine.getInstance();
                SecurityEngine.RefreshDevices();
            }
            String[] arrayOfString = EngineUtils.getInstance().enumUsers();
            boolean bool1 = bool2;
            if (arrayOfString != null)
            {
                bool1 = bool2;
                if (arrayOfString.length > 0)
                {
                    Applog.systemOut("-------checkPrivateKeyStatus------ " + arrayOfString[0]);
                    bool1 = bool2;
                    if (!TextUtils.isEmpty(arrayOfString[0]))
                    {
                        this.userPhone = arrayOfString[0];
                        bool1 = true;
                    }
                }
            }
            return bool1;
        }
        catch (SecEngineException localSecEngineException)
        {
            Applog.systemOut("----checkPrivateKeyStatus--SecEngineException-- " + localSecEngineException);
            ToastUtils.showShortToast(localSecEngineException.getMessage());
            return false;
        }
        catch (Exception localException)
        {
            Applog.systemOut("----------checkPrivateKeyStatus-----Exception----- " + localException);
        }
        return false;
    }

    private void initTipsDialog()
    {
        if (this.tipsDialog != null) {
            return;
        }
        this.tipsDialog = new TipsDialog.Build(this).setCancelable(false).setContent("������������������������,���������������������������(������������������������)").setDialogClickListener(new SplashActivity.3(this)).build();
    }

    private void jump()
    {
        if ((ChannelUtil.action_gesture) && (AppSpUtil.getInstanse().getGestureErrorCount() >= 5))
        {
            logoutAndToLoginView();
            finish();
            return;
        }
        if (LoginHelper.prepareUser())
        {
            this.password = UserSpUtil.getInstanse().getRandomNumber();
            if (!this.password.equals(""))
            {
                AppConfig.init();
                if (!EngineUtils.getInstance().initSecurityEngine(MjtApplication.getInstance())) {
                    break label78;
                }
                login();
            }
        }
        loginAfter();
        return;
        label78:
        Applog.systemOut("--------������TF���������-------");
        ToastUtils.showLongToastSafe("������������������������");
        this.loadingDialog.show();
        if (AppSpUtil.getInstanse().getBoundBracelet())
        {
            startService(new Intent(this, ConnectOldBraceletService.class));
            return;
        }
        startService(new Intent(this, ConfigNewBraceletService.class));
    }

    private void jumpForQh()
    {
        Applog.systemOut("------jumpForQh-----");
        if (EngineUtils.getInstance().initSecurityEngine(MjtApplication.getInstance()))
        {
            Applog.systemOut("-------initSecurityEngine������-----");
            if (checkPrivateKeyStatus())
            {
                Applog.systemOut("---------checkPrivateKeyStatus������----------");
                Bundle localBundle = new Bundle();
                localBundle.putString("key_phone", this.userPhone);
                ViewTransferUtil.transferToQHLoginActivityAndBundle(this, localBundle);
            }
        }
        for (;;)
        {
            finish();
            return;
            ToastUtils.showShortToast(2131690195);
            UserSpUtil.getInstanse().clearKey();
            IBCSpUtil.getInstanse().clearKey();
            LockPatternUtils.clearLockPattern();
            MjtApplication.getInstance().clearDatas();
            getContentResolver().delete(SipProfile.ACCOUNT_URI, null, null);
            getContentResolver().delete(SipManager.CALLLOG_URI, null, null);
            continue;
            ToastUtils.showShortToast(2131690172);
        }
    }

    private void login()
    {
        int i = 0;
        try
        {
            int j = UKeyManageInterface.getInstance().loginLocalDevice(UserSpUtil.getInstanse().getPhone(), this.password);
            i = j;
        }
        catch (SecEngineException localSecEngineException1)
        {
            try
            {
                for (;;)
                {
                    DBManager.init(MjtApplication.getInstance());
                    if ((MjtApplication.getInstance().getLoginUser() == null) || (MjtApplication.getInstance().getLoginUser().isErrorUser())) {
                        break;
                    }
                    this.jumpToMain = true;
                    return;
                    localSecEngineException1 = localSecEngineException1;
                    LogFinalUtils.logForException(localSecEngineException1);
                }
                try
                {
                    String str = UserSpUtil.getInstanse().getPhone();
                    if (str != null) {
                        EngineUtils.getInstance().deletePrivate(str);
                    }
                    EngineUtils.getInstance().logoutDevice();
                    UserSpUtil.getInstanse().deletePriKey();
                    return;
                }
                catch (SecEngineException localSecEngineException2)
                {
                    LogFinalUtils.logForException(localSecEngineException2);
                    localSecEngineException2.printStackTrace();
                    return;
                }
                return;
            }
            catch (Exception localException)
            {
                Applog.info("----DBManager--e--" + localException);
                Applog.systemOut("----DBManager--e--" + localException);
                this.jumpToMain = false;
            }
        }
        if (i != 1) {}
    }

    private void loginAfter()
    {
        Applog.systemOut("------jumpToMain----" + this.jumpToMain);
        BraceletFinishEvent.post(new BraceletFinishEvent());
        if (this.jumpToMain) {
            if (ChannelUtil.action_gesture) {
                if (AppSpUtil.getInstanse().getApplockGestureenable()) {
                    if (this.shareintent != null) {
                        LockPatternUtils.gotoCheckViewForMainShare(this, this.shareintent);
                    }
                }
            }
        }
        for (;;)
        {
            finish();
            return;
            LockPatternUtils.gotoMainCheckView(this);
            continue;
            if (this.shareintent != null)
            {
                this.shareintent.setComponent(new ComponentName(this, MJTMainActivity.class));
                startActivity(this.shareintent);
            }
            else
            {
                ViewTransferUtil.transferToMainActivity(this);
                continue;
                if (this.shareintent != null)
                {
                    this.shareintent.setComponent(new ComponentName(this, MJTMainActivity.class));
                    startActivity(this.shareintent);
                }
                else
                {
                    ViewTransferUtil.transferToMainActivity(this);
                    continue;
                    logoutAndToLoginView();
                }
            }
        }
    }

    private void logoutAndToLoginView()
    {
        UserSpUtil.getInstanse().clearKey();
        IBCSpUtil.getInstanse().clearKey();
        LockPatternUtils.clearLockPattern();
        MjtApplication.getInstance().clearDatas();
        getContentResolver().delete(SipProfile.ACCOUNT_URI, null, null);
        getContentResolver().delete(SipManager.CALLLOG_URI, null, null);
        ViewTransferUtil.transferToLoginActivity(this);
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleBraceletConnectSuccess(BraceletConnectSuccessEvent paramBraceletConnectSuccessEvent)
    {
        if (MjtApplication.getInstance().getLastActivity() == this)
        {
            Applog.systemOut("------handleBraceletConnectSuccess----- " + paramBraceletConnectSuccessEvent.isSuccess());
            if (!paramBraceletConnectSuccessEvent.isSuccess()) {
                break label65;
            }
            login();
            loginAfter();
        }
        for (;;)
        {
            this.loadingDialog.hide();
            Applog.systemOut("-----handleBraceletConnectSuccess-----");
            return;
            label65:
            if (!AppSpUtil.getInstanse().getBoundBracelet())
            {
                loginAfter();
            }
            else
            {
                initTipsDialog();
                this.tipsDialog.show();
            }
        }
    }

    public void onBackPressed() {}

    protected void onCreate(@Nullable Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        EventBusUtil.register(this);
        this.loadingDialog = new LoadingDialog(this);
        paramBundle = getIntent();
        String str1;
        if (paramBundle != null)
        {
            str1 = paramBundle.getAction();
            String str2 = paramBundle.getType();
            if (("android.intent.action.SEND".equals(str1)) && (str2 != null))
            {
                if (MjtApplication.getInstance().getMainSipCallSession() != null)
                {
                    ToastUtils.showShortToast(2131690231);
                    finish();
                    return;
                }
                this.shareintent = paramBundle;
                this.shareintent.setAction("");
                this.shareintent.putExtra("key_to_share", true);
                if (MjtApplication.getInstance().activities.size() != 1)
                {
                    paramBundle = MjtApplication.getInstance().getLoginUser();
                    if ((paramBundle != null) && (!paramBundle.isErrorUser()))
                    {
                        MjtApplication.getInstance().setGotoForegroundForShare(true);
                        this.shareintent.setComponent(new ComponentName(this, MJTMainActivity.class));
                        startActivity(this.shareintent);
                        finish();
                        return;
                    }
                    ViewTransferUtil.transferToLoginActivity(this);
                    finish();
                    return;
                }
            }
        }
        if (!isTaskRoot())
        {
            paramBundle = getIntent();
            if (paramBundle != null)
            {
                str1 = paramBundle.getAction();
                if ((paramBundle.hasCategory("android.intent.category.LAUNCHER")) && ("android.intent.action.MAIN".equals(str1)))
                {
                    Applog.systemOut("-----������������������APP��� ���������������������------");
                    finish();
                    return;
                }
            }
        }
        MjtApplication.getInstance().isAppNormal = true;
        paramBundle = new TipsDialog.Build(this).setDialogClickListener(new SplashActivity.1(this)).setContent(getResources().getString(2131690106)).setCancelable(false).setSingleChoise(true).build();
        SelfDistructCheck.check();
        if ((ChannelUtil.currentChannel == 102) && (AppSpUtil.getInstanse().getSelfDestruct()))
        {
            paramBundle.show();
            return;
        }
        if ((AppSpUtil.getInstanse().getFirstStart()) && (ChannelUtil.action_show_guide) && (DeviceInfoUtil.isZh(this)))
        {
            startActivity(new Intent(this, GuideActivity.class));
            finish();
            return;
        }
        AppSpUtil.getInstanse().setFirstStart(false);
        new Handler().postDelayed(new SplashActivity.2(this), 500L);
    }

    protected void onDestroy()
    {
        super.onDestroy();
        EventBusUtil.unregister(this);
        if ((this.loadingDialog != null) && (this.loadingDialog.isShowing())) {
            this.loadingDialog.hide();
        }
    }
}
