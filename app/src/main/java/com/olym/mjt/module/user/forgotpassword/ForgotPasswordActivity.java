package com.olym.mjt.module.user.forgotpassword;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.bracelet.ConfigNewBraceletService;
import com.olym.mjt.bracelet.ConnectOldBraceletService;
import com.olym.mjt.bracelet.event.BraceletConnectSuccessEvent;
import com.olym.mjt.databean.bean.UserDomainBean;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.utils.KeyboardHideUtil;
import com.olym.mjt.utils.sharedpreferencesutils.AppSpUtil;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;
import com.olym.mjt.widget.dialog.AreaCode;
import com.olym.mjt.widget.dialog.InputDialog;
import com.olym.mjt.widget.dialog.InputDialog.Build;
import com.olym.mjt.widget.dialog.LoadingDialog;
import com.olym.mjt.widget.dialog.SelectPhoneAreaCodeDialog;
import com.olym.mjt.widget.dialog.SelectPhoneAreaCodeDialog.Build;
import java.util.ArrayList;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427378)
public class ForgotPasswordActivity
        extends BaseTopbarActivity<ForgotPasswordPresenter>
        implements IForgotPasswordView
{
    private CountDownTimer countDownTimer;
    @ViewInject(2131230924)
    private EditText et_code;
    @ViewInject(2131230929)
    private EditText et_password;
    @ViewInject(2131230930)
    private EditText et_phone;
    @ViewInject(2131231045)
    private ImageView iv_password_eye;
    @ViewInject(2131231080)
    private View ll_area;
    private LoadingDialog loadingDialog;
    private View.OnClickListener onClickListener = new ForgotPasswordActivity.6(this);
    private SelectPhoneAreaCodeDialog selectPhoneAreaCodeDialog;
    @ViewInject(2131231435)
    private TextView tv_area_code;
    @ViewInject(2131231473)
    private TextView tv_get_code;
    @ViewInject(2131231486)
    private TextView tv_ok;
    @ViewInject(2131231539)
    private View v_line_code;
    @ViewInject(2131231543)
    private View v_line_pass;
    @ViewInject(2131231544)
    private View v_line_phone;

    private void initSelectPhoneAreaCodeDialog()
    {
        if (this.selectPhoneAreaCodeDialog == null) {
            this.selectPhoneAreaCodeDialog = new SelectPhoneAreaCodeDialog.Build(this).setDialogClickListener(new ForgotPasswordActivity.1(this)).build();
        }
    }

    private void inputCompanyDomain()
    {
        new InputDialog.Build(this).setTitle(getResources().getString(2131690053)).setContent(UserSpUtil.getInstanse().getUserDomain()).setDialogClickListener(new ForgotPasswordActivity.12(this)).build().show();
    }

    public void changeSuccess()
    {
        runOnUiThread(new ForgotPasswordActivity.9(this));
    }

    public void destroy()
    {
        this.countDownTimer.cancel();
        if (this.loadingDialog.isShowing()) {
            this.loadingDialog.dismiss();
        }
        if ((this.selectPhoneAreaCodeDialog != null) && (this.selectPhoneAreaCodeDialog.isShowing())) {
            this.selectPhoneAreaCodeDialog.dismiss();
        }
    }

    public void getCodeSuccess()
    {
        this.countDownTimer.start();
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleBraceletConnectSuccess(BraceletConnectSuccessEvent paramBraceletConnectSuccessEvent)
    {
        if (MjtApplication.getInstance().getLastActivity() == this)
        {
            if (!paramBraceletConnectSuccessEvent.isSuccess()) {
                break label46;
            }
            ((ForgotPasswordPresenter)this.presenter).forgetPassWord(this.et_phone.getText().toString().trim());
        }
        for (;;)
        {
            Applog.systemOut("-----handleBraceletConnectSuccess---ForgotPassowrd--");
            return;
            label46:
            hideLoading();
        }
    }

    public void handleBundle(Bundle paramBundle) {}

    public void hideLoading()
    {
        runOnUiThread(new ForgotPasswordActivity.8(this));
    }

    public void init()
    {
        setTitleText(getResources().getString(2131689804));
        KeyboardHideUtil.init(this);
        this.loadingDialog = new LoadingDialog(this);
        ChannelUtil.initAreaCodes();
        this.ll_area.setOnClickListener(this.onClickListener);
        this.tv_area_code.setText(ChannelUtil.currentArea.toString());
        this.countDownTimer = new ForgotPasswordActivity.2(this, 60000L, 1000L);
        this.et_phone.setOnFocusChangeListener(new ForgotPasswordActivity.3(this));
        this.et_code.setOnFocusChangeListener(new ForgotPasswordActivity.4(this));
        this.et_password.setTypeface(Typeface.DEFAULT);
        this.et_password.setOnFocusChangeListener(new ForgotPasswordActivity.5(this));
        this.tv_get_code.setOnClickListener(this.onClickListener);
        this.iv_password_eye.setOnClickListener(this.onClickListener);
        this.tv_ok.setOnClickListener(this.onClickListener);
        this.iv_password_eye.setSelected(false);
        this.tv_get_code.setEnabled(true);
    }

    public void selectCompanyDoaminList(ArrayList<UserDomainBean> paramArrayList)
    {
        runOnUiThread(new ForgotPasswordActivity.10(this, paramArrayList));
    }

    public void setCompanyDomain()
    {
        if (!ChannelUtil.domain.equals(""))
        {
            Applog.systemOut("----setCompanyDomain---������������--" + ChannelUtil.domain);
            ((ForgotPasswordPresenter)this.presenter).downloadAddress(ChannelUtil.domain, false);
            return;
        }
        runOnUiThread(new ForgotPasswordActivity.11(this));
    }

    protected void setPresenter()
    {
        this.presenter = new ForgotPasswordPresenter(this);
    }

    public void showLoading()
    {
        runOnUiThread(new ForgotPasswordActivity.7(this));
    }

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

