package com.olym.mjt.module.user.register;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.olym.mjt.utils.EventBusUtil;
import com.olym.mjt.utils.KeyboardHideUtil;
import com.olym.mjt.utils.ViewTransferUtil;
import com.olym.mjt.utils.sharedpreferencesutils.AppSpUtil;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;
import com.olym.mjt.widget.dialog.AreaCode;
import com.olym.mjt.widget.dialog.InputDialog;
import com.olym.mjt.widget.dialog.InputDialog.Build;
import com.olym.mjt.widget.dialog.LoadingDialog;
import com.olym.mjt.widget.dialog.SelectPhoneAreaCodeDialog;
import com.olym.mjt.widget.dialog.SelectPhoneAreaCodeDialog.Build;
import com.olym.mjt.widget.dialog.TipsDialog;
import com.olym.mjt.widget.dialog.TipsDialog.Build;
import java.util.ArrayList;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427422)
public class RegisterActivity
        extends BaseTopbarActivity<RegisterPresenter>
        implements IRegisterView
{
    public static final String INTENT_DOMAIN = "from_loginActiivty_domain";
    public static final String INTENT_PHONE = "from_loginActivity_phone";
    private TipsDialog braceletTipsDialog;
    @ViewInject(2131230928)
    private EditText et_nickname;
    @ViewInject(2131230929)
    private EditText et_password;
    @ViewInject(2131230930)
    private EditText et_phone;
    private String intentDomain;
    private String intentPhone;
    @ViewInject(2131231027)
    private ImageView iv_check_photo;
    @ViewInject(2131231045)
    private ImageView iv_password_eye;
    @ViewInject(2131231080)
    private View ll_area;
    private LoadingDialog loadingDialog;
    private View.OnClickListener onClickListener = new RegisterActivity.6(this);
    private SelectPhoneAreaCodeDialog selectPhoneAreaCodeDialog;
    @ViewInject(2131231435)
    private TextView tv_area_code;
    @ViewInject(2131231486)
    private TextView tv_ok;
    @ViewInject(2131231542)
    private View v_line_nickname;
    @ViewInject(2131231543)
    private View v_line_pass;
    @ViewInject(2131231544)
    private View v_line_phone;

    private void initBraceletTipsDialog()
    {
        if (this.braceletTipsDialog == null) {
            this.braceletTipsDialog = new TipsDialog.Build(this).setCancelable(false).setSingleChoise(true).setContent("������������������������?").setDialogClickListener(new RegisterActivity.1(this)).build();
        }
    }

    private void initSelectPhoneAreaCodeDialog()
    {
        if (this.selectPhoneAreaCodeDialog == null) {
            this.selectPhoneAreaCodeDialog = new SelectPhoneAreaCodeDialog.Build(this).setDialogClickListener(new RegisterActivity.2(this)).build();
        }
    }

    private void inputCompanyDomain()
    {
        new InputDialog.Build(this).setTitle(getResources().getString(2131690053)).setContent(UserSpUtil.getInstanse().getUserDomain()).setDialogClickListener(new RegisterActivity.12(this)).build().show();
    }

    public void backToLoginActivity()
    {
        finish();
    }

    public void destroy()
    {
        if (this.loadingDialog.isShowing()) {
            this.loadingDialog.dismiss();
        }
        EventBusUtil.unregister(this);
    }

    public void getCodeSuccess(String paramString1, String paramString2)
    {
        hideLoading();
        Bundle localBundle = new Bundle();
        localBundle.putString("action", "register");
        localBundle.putString("phone", paramString1);
        localBundle.putString("pass", paramString2);
        localBundle.putString("nickname", this.et_nickname.getText().toString().trim());
        ViewTransferUtil.transferToCodeActivity(this, localBundle);
        finish();
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleBraceletConnectSuccess(BraceletConnectSuccessEvent paramBraceletConnectSuccessEvent)
    {
        if (MjtApplication.getInstance().getLastActivity() == this)
        {
            if (!paramBraceletConnectSuccessEvent.isSuccess()) {
                break label60;
            }
            ((RegisterPresenter)this.presenter).getSmsCode(this.et_phone.getText().toString().trim(), this.et_password.getText().toString().trim());
        }
        for (;;)
        {
            Applog.systemOut("-----handleBraceletConnectSuccess---RegisterActivity--");
            return;
            label60:
            hideLoading();
        }
    }

    public void handleBundle(Bundle paramBundle)
    {
        this.intentPhone = paramBundle.getString("from_loginActivity_phone");
        this.intentDomain = paramBundle.getString("from_loginActiivty_domain");
    }

    public void hideLoading()
    {
        runOnUiThread(new RegisterActivity.9(this));
    }

    public void init()
    {
        setTitleText(getResources().getString(2131690125));
        KeyboardHideUtil.init(this);
        this.loadingDialog = new LoadingDialog(this);
        ChannelUtil.initAreaCodes();
        EventBusUtil.register(this);
        this.ll_area.setOnClickListener(this.onClickListener);
        this.tv_area_code.setText(ChannelUtil.currentArea.toString());
        this.iv_password_eye.setOnClickListener(this.onClickListener);
        this.iv_password_eye.setSelected(false);
        if (!TextUtils.isEmpty(this.intentPhone)) {
            this.et_phone.setText(this.intentPhone);
        }
        this.tv_ok.setOnClickListener(this.onClickListener);
        this.et_nickname.setOnFocusChangeListener(new RegisterActivity.3(this));
        this.et_phone.setOnFocusChangeListener(new RegisterActivity.4(this));
        this.et_password.setTypeface(Typeface.DEFAULT);
        this.et_password.setOnFocusChangeListener(new RegisterActivity.5(this));
    }

    public void selectCompanyDoaminList(ArrayList<UserDomainBean> paramArrayList)
    {
        runOnUiThread(new RegisterActivity.10(this, paramArrayList));
    }

    public void setCompanyDomain()
    {
        if (!ChannelUtil.domain.equals(""))
        {
            Applog.systemOut("----setCompanyDomain---������������--" + ChannelUtil.domain);
            ((RegisterPresenter)this.presenter).downloadAddress(ChannelUtil.domain, false);
            return;
        }
        runOnUiThread(new RegisterActivity.11(this));
    }

    protected void setPresenter()
    {
        this.presenter = new RegisterPresenter(this);
    }

    public void showBraceletTipsDialog()
    {
        runOnUiThread(new RegisterActivity.13(this));
    }

    public void showLoading()
    {
        runOnUiThread(new RegisterActivity.8(this));
    }

    public void showTipsDialog(String paramString1, String paramString2)
    {
        new TipsDialog.Build(this).setTitle(getResources().getString(2131689731)).setContent(getResources().getString(2131689732) + "\n+" + ChannelUtil.currentArea.getCode() + paramString1).setCancelable(false).setDialogClickListener(new RegisterActivity.7(this, paramString1)).build().show();
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
