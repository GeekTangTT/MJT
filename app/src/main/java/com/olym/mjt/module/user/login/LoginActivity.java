package com.olym.mjt.module.user.login;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.olym.mjt.base.activity.BasePresenterActivity;
import com.olym.mjt.bracelet.ConfigNewBraceletService;
import com.olym.mjt.bracelet.ConnectOldBraceletService;
import com.olym.mjt.bracelet.event.BraceletConnectSuccessEvent;
import com.olym.mjt.databean.bean.UserDomainBean;
import com.olym.mjt.databean.event.ExceptionLogoutEvent;
import com.olym.mjt.databean.event.LoginSuccessEvent;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.module.message.tools.shortcutbadger.ShortcutBadger;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.utils.EventBusUtil;
import com.olym.mjt.utils.KeyboardHideUtil;
import com.olym.mjt.utils.StatusBarUtil;
import com.olym.mjt.utils.ViewTransferUtil;
import com.olym.mjt.utils.sharedpreferencesutils.AppSpUtil;
import com.olym.mjt.utils.sharedpreferencesutils.IBCSpUtil;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;
import com.olym.mjt.widget.applock.LockPatternUtils;
import com.olym.mjt.widget.dialog.AreaCode;
import com.olym.mjt.widget.dialog.InputDialog;
import com.olym.mjt.widget.dialog.InputDialog.Build;
import com.olym.mjt.widget.dialog.LoadingDialog;
import com.olym.mjt.widget.dialog.SelectPhoneAreaCodeDialog;
import com.olym.mjt.widget.dialog.SelectPhoneAreaCodeDialog.Build;
import com.olym.mjt.widget.dialog.TipsDialog;
import com.olym.mjt.widget.dialog.TipsDialog.Build;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427392)
public class LoginActivity
        extends BasePresenterActivity<LoginPresenter>
        implements ILoginView
{
    private TipsDialog braceletTipsDialog;
    @ViewInject(2131230929)
    private EditText et_password;
    @ViewInject(2131230930)
    private EditText et_phone;
    private boolean ishandleSelfDestruct = false;
    @ViewInject(2131231039)
    private ImageView iv_icon;
    @ViewInject(2131231045)
    private ImageView iv_password_eye;
    @ViewInject(2131231080)
    private View ll_area;
    private LoadingDialog loadingDialog;
    private long[] mHits = null;
    private View.OnClickListener onClickListener = new LoginActivity.6(this);
    private SelectPhoneAreaCodeDialog selectPhoneAreaCodeDialog;
    private TipsDialog selfDestructTipsDialog;
    @ViewInject(2131231435)
    private TextView tv_area_code;
    @ViewInject(2131231458)
    private TextView tv_domain;
    @ViewInject(2131231471)
    private TextView tv_forgotpassword;
    @ViewInject(2131231478)
    private TextView tv_login;
    @ViewInject(2131231494)
    private TextView tv_register;
    @ViewInject(2131231543)
    private View v_line_pass;
    @ViewInject(2131231544)
    private View v_line_phone;

    private boolean handleSelfDestruct(Intent paramIntent)
    {
        if (paramIntent.getBooleanExtra("key_self_destruct", false))
        {
            Applog.systemOut("------handleAllLogout---KEY_SELF_DESTRUCT--");
            this.ishandleSelfDestruct = true;
            if (this.selfDestructTipsDialog == null) {
                this.selfDestructTipsDialog = new TipsDialog.Build(this).setDialogClickListener(new LoginActivity.14(this)).setContent(getResources().getString(2131690106)).setCancelable(false).setSingleChoise(true).build();
            }
            this.selfDestructTipsDialog.show();
            return true;
        }
        return false;
    }

    private void initBraceletTipsDialog()
    {
        if (this.braceletTipsDialog == null) {
            this.braceletTipsDialog = new TipsDialog.Build(this).setCancelable(false).setSingleChoise(true).setContent("������������������������?").setDialogClickListener(new LoginActivity.1(this)).build();
        }
    }

    private void initSelectPhoneAreaCodeDialog()
    {
        if (this.selectPhoneAreaCodeDialog == null) {
            this.selectPhoneAreaCodeDialog = new SelectPhoneAreaCodeDialog.Build(this).setDialogClickListener(new LoginActivity.2(this)).build();
        }
    }

    private void inputCompanyDomain()
    {
        new InputDialog.Build(this).setTitle(getResources().getString(2131690053)).setContent(UserSpUtil.getInstanse().getUserDomain()).setDialogClickListener(new LoginActivity.12(this)).build().show();
    }

    private void logout()
    {
        ShortcutBadger.removeCount(MjtApplication.getInstance());
        UserSpUtil.getInstanse().clearKey();
        IBCSpUtil.getInstanse().clearKey();
        LockPatternUtils.clearLockPattern();
        MjtApplication.getInstance().clearDatas();
    }

    public void destroy()
    {
        EventBusUtil.unregister(this);
        if (this.loadingDialog.isShowing()) {
            this.loadingDialog.dismiss();
        }
        if ((this.selectPhoneAreaCodeDialog != null) && (this.selectPhoneAreaCodeDialog.isShowing())) {
            this.selectPhoneAreaCodeDialog.dismiss();
        }
    }

    public void getCodeSuccess(String paramString1, String paramString2)
    {
        hideLoading();
        Bundle localBundle = new Bundle();
        localBundle.putString("action", "login");
        localBundle.putString("phone", paramString1);
        localBundle.putString("pass", paramString2);
        ViewTransferUtil.transferToCodeActivity(this, localBundle);
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleBraceletConnectSuccess(BraceletConnectSuccessEvent paramBraceletConnectSuccessEvent)
    {
        if (MjtApplication.getInstance().getLastActivity() == this)
        {
            if (!paramBraceletConnectSuccessEvent.isSuccess()) {
                break label34;
            }
            ((LoginPresenter)this.presenter).getSipAuthKey();
        }
        for (;;)
        {
            Applog.systemOut("-----handleBraceletConnectSuccess-----");
            return;
            label34:
            hideLoading();
        }
    }

    public void handleBundle(Bundle paramBundle) {}

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleExceptionLogout(ExceptionLogoutEvent paramExceptionLogoutEvent)
    {
        paramExceptionLogoutEvent = paramExceptionLogoutEvent.getKey();
        if (paramExceptionLogoutEvent.equals("key_self_destruct"))
        {
            Applog.systemOut("-------handleExceptionLogout---loginactivity----- " + paramExceptionLogoutEvent);
            Applog.info("-------handleExceptionLogout----loginactivity---- " + paramExceptionLogoutEvent);
            logout();
            Intent localIntent = new Intent(this, LoginActivity.class);
            localIntent.putExtra(paramExceptionLogoutEvent, true);
            localIntent.addFlags(268435456);
            startActivity(localIntent);
        }
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleLoginSuccess(LoginSuccessEvent paramLoginSuccessEvent)
    {
        finish();
    }

    public void hideLoading()
    {
        runOnUiThread(new LoginActivity.9(this));
    }

    public void init()
    {
        StatusBarUtil.setStatusBar(this);
        this.loadingDialog = new LoadingDialog(this);
        KeyboardHideUtil.init(this);
        MjtApplication.getInstance().isAppNormal = true;
        UserSpUtil.getInstanse().setNewLogin(true);
        UserSpUtil.getInstanse().deletePriKey();
        ChannelUtil.initAreaCodes();
        this.tv_area_code.setText(ChannelUtil.currentArea.toString());
        this.ll_area.setOnClickListener(this.onClickListener);
        this.tv_domain.setOnClickListener(this.onClickListener);
        this.iv_password_eye.setOnClickListener(this.onClickListener);
        this.tv_forgotpassword.setOnClickListener(this.onClickListener);
        this.tv_login.setOnClickListener(this.onClickListener);
        this.tv_register.setOnClickListener(this.onClickListener);
        this.iv_password_eye.setSelected(false);
        EventBusUtil.register(this);
        this.et_phone.setOnFocusChangeListener(new LoginActivity.3(this));
        this.et_password.setTypeface(Typeface.DEFAULT);
        this.et_password.setOnFocusChangeListener(new LoginActivity.4(this));
        this.iv_icon.setImageResource(ChannelUtil.icon_login);
        this.iv_icon.setOnClickListener(new LoginActivity.5(this));
    }

    public void intentRegisterActivity(String paramString1, String paramString2)
    {
        Bundle localBundle = new Bundle();
        localBundle.putString("from_loginActivity_phone", paramString1);
        localBundle.putString("from_loginActiivty_domain", paramString2);
        ViewTransferUtil.transferToRegisterActivity(this, localBundle);
    }

    public void onBackPressed()
    {
        finish();
    }

    protected void onNewIntent(Intent paramIntent)
    {
        super.onNewIntent(paramIntent);
        handleSelfDestruct(paramIntent);
    }

    protected void onResume()
    {
        super.onResume();
        handleSelfDestruct(getIntent());
    }

    public void selectCompanyDoaminList(List<UserDomainBean> paramList)
    {
        runOnUiThread(new LoginActivity.11(this, paramList));
    }

    public void setCompanyDomain()
    {
        if (!ChannelUtil.domain.equals(""))
        {
            Applog.systemOut("----setCompanyDomain---������������--" + ChannelUtil.domain);
            showLoading();
            ((LoginPresenter)this.presenter).downloadAddress(ChannelUtil.domain, false);
            return;
        }
        Applog.systemOut("----setCompanyDomain---������������--");
        runOnUiThread(new LoginActivity.10(this));
    }

    protected void setPresenter()
    {
        this.presenter = new LoginPresenter(this);
    }

    public void showBraceletTipsDialog()
    {
        runOnUiThread(new LoginActivity.13(this));
    }

    public void showLoading()
    {
        runOnUiThread(new LoginActivity.8(this));
    }

    public void showTipsDialog(String paramString)
    {
        runOnUiThread(new LoginActivity.7(this, paramString));
    }

    public void showToast(String paramString) {}

    public void startBraceletService()
    {
        if (AppSpUtil.getInstanse().getBoundBracelet())
        {
            startService(new Intent(this, ConnectOldBraceletService.class));
            return;
        }
        startService(new Intent(this, ConfigNewBraceletService.class));
    }
}

