package com.olym.mjt.module.user.code;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.bracelet.event.BraceletFinishEvent;
import com.olym.mjt.databean.event.LoginSuccessEvent;
import com.olym.mjt.pjsip.sip.api.SipProfile;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.utils.KeyboardHideUtil;
import com.olym.mjt.utils.ToastUtils;
import com.olym.mjt.utils.ViewTransferUtil;
import com.olym.mjt.widget.applock.LockPatternUtils;
import com.olym.mjt.widget.dialog.LoadingDialog;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427364)
public class CodeActivity
        extends BaseTopbarActivity<CodePresenter>
        implements ICodeView
{
    public static final String ACTION_LOGIN = "login";
    public static final String ACTION_REGISTER = "register";
    public static final String KEY_ACTION = "action";
    public static final String KEY_NICKNAME = "nickname";
    public static final String KEY_PASS = "pass";
    public static final String KEY_PHONE = "phone";
    private String action;
    private CountDownTimer countDownTimer;
    @ViewInject(2131230924)
    private EditText et_code;
    private LoadingDialog loadingDialog;
    private String nickname;
    private View.OnClickListener onClickListener = new CodeActivity.2(this);
    private String pass;
    private String phone;
    @ViewInject(2131231473)
    private TextView tv_get_code;
    @ViewInject(2131231486)
    private TextView tv_ok;
    @ViewInject(2131231488)
    private TextView tv_phone;

    public void destroy()
    {
        this.countDownTimer.cancel();
        if (this.loadingDialog.isShowing()) {
            this.loadingDialog.dismiss();
        }
        BraceletFinishEvent.post(new BraceletFinishEvent());
    }

    public void finishActivity()
    {
        finish();
    }

    public void getCodeSuccess()
    {
        this.countDownTimer.start();
    }

    public void handleBundle(Bundle paramBundle)
    {
        this.action = paramBundle.getString("action", null);
        this.phone = paramBundle.getString("phone", null);
        this.pass = paramBundle.getString("pass", null);
        this.nickname = paramBundle.getString("nickname", null);
    }

    public void hideLoading()
    {
        runOnUiThread(new CodeActivity.4(this));
    }

    public void init()
    {
        setTitleText(getResources().getString(2131690119));
        KeyboardHideUtil.init(this);
        this.loadingDialog = new LoadingDialog(this);
        ChannelUtil.initAreaCodes();
        this.tv_phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        this.countDownTimer = new CodeActivity.1(this, 60000L, 1000L);
        this.countDownTimer.start();
        this.tv_get_code.setOnClickListener(this.onClickListener);
        this.tv_ok.setOnClickListener(this.onClickListener);
        this.tv_phone.setText(this.phone);
    }

    public void loginSuccess()
    {
        hideLoading();
        getContentResolver().delete(SipProfile.ACCOUNT_URI, null, null);
        if ((ChannelUtil.action_gesture) && (ChannelUtil.action_gesture_tips)) {
            LockPatternUtils.gotoMainSetView(this);
        }
        for (;;)
        {
            ToastUtils.showLongToastSafe(getResources().getString(2131690193));
            LoginSuccessEvent.post(new LoginSuccessEvent());
            finish();
            return;
            ViewTransferUtil.transferToMainActivity(this);
        }
    }

    protected void setPresenter()
    {
        this.presenter = new CodePresenter(this);
    }

    public void showLoading()
    {
        runOnUiThread(new CodeActivity.3(this));
    }
}

