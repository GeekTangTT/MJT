package com.olym.mjt.module.applock.gesture;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.databean.event.ExceptionLogoutEvent;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.module.message.tools.shortcutbadger.ShortcutBadger;
import com.olym.mjt.module.notification.NotificationService;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.EventBusUtil;
import com.olym.mjt.utils.ViewTransferUtil;
import com.olym.mjt.utils.sharedpreferencesutils.IBCSpUtil;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;
import com.olym.mjt.widget.applock.LockPatternUtils;
import com.olym.mjt.widget.applock.LockPatternView;
import com.olym.mjt.widget.applock.LockPatternViewPattern;
import com.olym.mjt.widget.dialog.TipsDialog;
import com.olym.mjt.widget.dialog.TipsDialog.Build;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427382)
public class GestureActivity
        extends BaseTopbarActivity<GesturePresenter>
        implements IGestureView
{
    public static final int EXTRA_DATA_GOTO_MAIN = 1;
    public static final String KEY_EXTRA_DATA = "extra_data";
    public static final String KEY_TYPE = "type";
    public static final int TYPE_CHECK = 3;
    public static final int TYPE_CLOSE_CHECK = 5;
    public static final int TYPE_RESET = 2;
    public static final int TYPE_SET = 1;
    private static final int TYPE_SET_AGAIN = 4;
    private TipsDialog errorPasswordTipsDialog;
    private int extra_data;
    private TipsDialog forgotPasswordTipsDialog;
    private LockPatternViewPattern lockPatternViewPattern;
    @ViewInject(2131231140)
    private LockPatternView lockview;
    private Runnable mClearPatternRunnable = new GestureActivity.3(this);
    private String pass1;
    private TipsDialog selfDestructTipsDialog;
    private Intent shareIntent;
    @ViewInject(2131231471)
    private TextView tv_forgotpassword;
    @ViewInject(2131231509)
    private TextView tv_tips;
    private int type;

    private void initErrorPasswordTipsDialog()
    {
        if (this.errorPasswordTipsDialog == null) {
            this.errorPasswordTipsDialog = new TipsDialog.Build(this).setContent(getResources().getString(2131689723)).setCancelable(false).setSingleChoise(true).setDialogClickListener(new GestureActivity.5(this)).build();
        }
    }

    private void initForgotPasswordTipsDialog()
    {
        if (this.forgotPasswordTipsDialog == null) {
            this.forgotPasswordTipsDialog = new TipsDialog.Build(this).setContent(getResources().getString(2131689722)).setDialogClickListener(new GestureActivity.4(this)).build();
        }
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
    }

    public void handleBundle(Bundle paramBundle)
    {
        this.type = paramBundle.getInt("type", -1);
        this.extra_data = paramBundle.getInt("extra_data", -1);
        if (this.type == -1) {
            finish();
        }
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleExceptionLogout(ExceptionLogoutEvent paramExceptionLogoutEvent)
    {
        paramExceptionLogoutEvent = paramExceptionLogoutEvent.getKey();
        if ((paramExceptionLogoutEvent.equals("key_self_destruct")) && (!NotificationService.isStarted))
        {
            Applog.systemOut("-------handleExceptionLogout---loginactivity----- " + paramExceptionLogoutEvent);
            Applog.info("-------handleExceptionLogout----loginactivity---- " + paramExceptionLogoutEvent);
            logout();
            if (this.selfDestructTipsDialog == null) {
                this.selfDestructTipsDialog = new TipsDialog.Build(this).setDialogClickListener(new GestureActivity.6(this)).setContent(getResources().getString(2131690106)).setCancelable(false).setSingleChoise(true).build();
            }
            this.selfDestructTipsDialog.show();
        }
    }

    public void init()
    {
        MjtApplication.getInstance().setShouldStartGesture(false);
        EventBusUtil.register(this);
        this.tv_forgotpassword.setOnClickListener(new GestureActivity.1(this));
        this.lockPatternViewPattern = new LockPatternViewPattern(this.lockview);
        this.lockPatternViewPattern.setPatternListener(new GestureActivity.2(this));
        this.lockview.setOnPatternListener(this.lockPatternViewPattern);
        this.lockview.setTactileFeedbackEnabled(true);
        if (this.type == 1)
        {
            this.tv_tips.setText(getResources().getString(2131690173));
            setTitleText(getResources().getString(2131690230));
            this.tv_forgotpassword.setVisibility(8);
        }
        for (;;)
        {
            Intent localIntent = getIntent();
            if ((localIntent != null) && (localIntent.getBooleanExtra("key_to_share", false)))
            {
                this.shareIntent = localIntent;
                Applog.systemOut("-----shareIntent----- " + this.shareIntent);
            }
            return;
            if (this.type == 2)
            {
                this.tv_tips.setText(getResources().getString(2131689850));
                setTitleText(getResources().getString(2131689648));
                this.tv_forgotpassword.setVisibility(0);
            }
            else if ((this.type == 3) || (this.type == 5))
            {
                this.tv_tips.setText(getResources().getString(2131690173));
                setTitleText(getResources().getString(2131689666));
                setBackButtonGone();
                this.tv_forgotpassword.setVisibility(0);
                MjtApplication.getInstance().setCheckdGesture(true);
            }
        }
    }

    public void onBackPressed()
    {
        if ((this.type == 3) || (this.type == 5))
        {
            Intent localIntent = new Intent("android.intent.action.MAIN");
            localIntent.setFlags(268435456);
            localIntent.addCategory("android.intent.category.HOME");
            startActivity(localIntent);
            return;
        }
        if (this.extra_data == 1) {
            ViewTransferUtil.transferToMainActivity(this);
        }
        super.onBackPressed();
    }

    protected void setPresenter()
    {
        this.presenter = new GesturePresenter(this);
    }
}
