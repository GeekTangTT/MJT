package com.olym.mjt.module.user.qhlogin;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.TextView;
import com.lc.methodex.LogFinalUtils;
import com.nisc.SecurityEngine;
import com.nisc.SecurityEngineException;
import com.nisc.UKeyManageInterface;
import com.nisc.api.SecEngineException;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.config.AppConfig;
import com.olym.mjt.database.DBManager;
import com.olym.mjt.databean.bean.User;
import com.olym.mjt.databean.event.ExceptionLogoutEvent;
import com.olym.mjt.init.EngineUtils;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.network.client.HttpsClient;
import com.olym.mjt.pjsip.sip.api.SipManager;
import com.olym.mjt.pjsip.sip.api.SipProfile;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.utils.EventBusUtil;
import com.olym.mjt.utils.FileSpUtils;
import com.olym.mjt.utils.LoginHelper;
import com.olym.mjt.utils.ToastUtils;
import com.olym.mjt.utils.ViewTransferUtil;
import com.olym.mjt.utils.sharedpreferencesutils.AppSpUtil;
import com.olym.mjt.utils.sharedpreferencesutils.IBCSpUtil;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;
import com.olym.mjt.widget.CodeView;
import com.olym.mjt.widget.applock.LockPatternUtils;
import com.olym.mjt.widget.dialog.LoadingDialog;
import java.net.UnknownHostException;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427393)
public class QHLoginActivity
        extends BaseTopbarActivity<QHLoginPresenter>
        implements IQHLoginView
{
    public static final String KEY_PHONE = "key_phone";
    @ViewInject(2131230878)
    private CodeView codeView;
    private Handler handler = new Handler();
    private LoadingDialog loadingDialog;
    private String phone;
    @ViewInject(2131231483)
    private TextView tv_next;
    @ViewInject(2131231497)
    private TextView tv_reset_pin;
    @ViewInject(2131231509)
    private TextView tv_tips;

    private void getDomainInfo()
    {
        Object localObject = "";
        try
        {
            String str = EngineUtils.getInstance().getHostByName("mobile.hcitsec.com");
            localObject = str;
        }
        catch (UnknownHostException localUnknownHostException)
        {
            for (;;)
            {
                LogFinalUtils.logForException(localUnknownHostException);
                Applog.systemOut("------UnknownHostException---- " + localUnknownHostException);
            }
        }
        UserSpUtil.getInstanse().setUserIP((String)localObject);
        UserSpUtil.getInstanse().setUserDomian(ChannelUtil.domain);
        FileSpUtils.getInstanse().setUserDomian(ChannelUtil.domain);
        UserSpUtil.getInstanse().setUserUrl("mobile.hcitsec.com");
        UserSpUtil.getInstanse().setUserPort(":443");
        UserSpUtil.getInstanse().setIpCheckDate(System.currentTimeMillis());
        AppConfig.init();
        getSipAuthKey();
    }

    private void getSipAuthKey()
    {
        HttpsClient.getInstanse().get_sip_auth_key(this.phone, new QHLoginActivity.6(this));
    }

    private void getSipPasswordEx()
    {
        HttpsClient.getInstanse().getSipPassword(this.phone, new QHLoginActivity.7(this));
    }

    private void handleCheckType(CharSequence paramCharSequence)
    {
        paramCharSequence = paramCharSequence.toString();
        int[] arrayOfInt = new int[1];
        try
        {
            showLoading();
            i = SecurityEngine.getInstance().TEEverifyPIN(paramCharSequence, arrayOfInt);
            Applog.systemOut("---result---- " + i);
            if (i == 1)
            {
                Applog.systemOut("------PIN���������------ " + MjtApplication.getInstance().getLoginUser());
                if ((LoginHelper.prepareUser()) && (MjtApplication.getInstance().getLoginUser() != null) && (!MjtApplication.getInstance().getLoginUser().isErrorUser()) && (MjtApplication.getInstance().getLoginUser().getTelephone().equals(this.phone))) {
                    login();
                }
                for (;;)
                {
                    this.handler.post(new QHLoginActivity.5(this));
                    return;
                    AppSpUtil.getInstanse().setInitContacts(false);
                    AppSpUtil.getInstanse().setInitRoom(false);
                    UserSpUtil.getInstanse().clearKey();
                    IBCSpUtil.getInstanse().clearKey();
                    LockPatternUtils.clearLockPattern();
                    MjtApplication.getInstance().clearDatas();
                    getContentResolver().delete(SipManager.CALLLOG_URI, null, null);
                    getDomainInfo();
                }
            }
            if (i != 61529) {
                break label306;
            }
        }
        catch (SecurityEngineException paramCharSequence)
        {
            Applog.systemOut("----TEEverifyPIN--SecurityEngineException-- " + paramCharSequence);
            ToastUtils.showShortToastSafe(paramCharSequence.getMessage());
            return;
        }
        int i = arrayOfInt[0];
        if (i > 0) {
            ToastUtils.showShortToastSafe(getResources().getString(2131690210) + i + getResources().getString(2131690211));
        }
        for (;;)
        {
            hideLoading();
            break;
            ToastUtils.showShortToastSafe(2131690197);
            continue;
            label306:
            if (i == 61528)
            {
                ToastUtils.showShortToastSafe(2131690197);
            }
            else if (i == 61535)
            {
                ToastUtils.showShortToastSafe(2131690197);
            }
            else
            {
                paramCharSequence = new SecEngineException(i);
                ToastUtils.showShortToastSafe(getResources().getString(2131690151) + paramCharSequence.getMessage());
            }
        }
    }

    private void login()
    {
        try
        {
            if (UKeyManageInterface.getInstance().loginLocalDevice(UserSpUtil.getInstanse().getPhone(), "") == 1)
            {
                AppConfig.init();
                DBManager.init(MjtApplication.getInstance());
                ViewTransferUtil.transferToMainActivity(this);
            }
            for (;;)
            {
                hideLoading();
                finish();
                return;
                ToastUtils.showShortToastSafe(2131690192);
            }
        }
        catch (SecEngineException localSecEngineException)
        {
            for (;;)
            {
                LogFinalUtils.logForException(localSecEngineException);
                Applog.systemOut("------SecEngineException---- " + localSecEngineException);
                ToastUtils.showShortToastSafe(localSecEngineException.getMessage());
            }
        }
        catch (Exception localException)
        {
            for (;;)
            {
                LogFinalUtils.logForException(localException);
                Applog.systemOut("------Exception---- " + localException);
                ToastUtils.showShortToastSafe(2131690191);
            }
        }
    }

    private void loginSuccess()
    {
        getContentResolver().delete(SipProfile.ACCOUNT_URI, null, null);
        if ((ChannelUtil.action_gesture) && (ChannelUtil.action_gesture_tips)) {
            LockPatternUtils.gotoMainSetView(this);
        }
        for (;;)
        {
            ToastUtils.showLongToastSafe(getResources().getString(2131690193));
            hideLoading();
            finish();
            return;
            ViewTransferUtil.transferToMainActivity(this);
        }
    }

    private void loginXmppEx()
    {
        HttpsClient.getInstanse().loginXmpp(this.phone, new QHLoginActivity.8(this));
    }

    public void destroy()
    {
        if (this.loadingDialog.isShowing()) {
            this.loadingDialog.dismiss();
        }
        EventBusUtil.unregister(this);
    }

    public void handleBundle(Bundle paramBundle)
    {
        this.phone = paramBundle.getString("key_phone");
        Applog.systemOut("---QHLoginActivity----phone------ " + this.phone);
        Applog.info("-----QHLoginActivity--phone------ " + this.phone);
        if (TextUtils.isEmpty(this.phone)) {
            finish();
        }
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleExceptionLogout(ExceptionLogoutEvent paramExceptionLogoutEvent)
    {
        finish();
    }

    public void hideLoading()
    {
        this.handler.post(new QHLoginActivity.4(this));
    }

    public void init()
    {
        setTitleText(getResources().getString(2131689667));
        this.tv_tips.setText(getResources().getString(2131689856));
        this.tv_reset_pin.setVisibility(0);
        this.tv_next.setVisibility(4);
        EventBusUtil.register(this);
        this.tv_reset_pin.setOnClickListener(new QHLoginActivity.1(this));
        this.loadingDialog = new LoadingDialog(this);
        this.codeView.addTextChangedListener(new QHLoginActivity.2(this));
    }

    protected void setPresenter()
    {
        this.presenter = new QHLoginPresenter(this);
    }

    public void showLoading()
    {
        this.handler.post(new QHLoginActivity.3(this));
    }
}
