package com.olym.mjt.module.applock;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.utils.sharedpreferencesutils.AppSpUtil;
import com.olym.mjt.widget.dialog.TipsDialog;
import com.olym.mjt.widget.dialog.TipsDialog.Build;
import com.suke.widget.SwitchButton;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427357)
public class AppLockActivity
        extends BaseTopbarActivity<AppLockPresenter>
        implements IAppLockView
{
    @ViewInject(2131231312)
    private SwitchButton sb_gesture;
    private TipsDialog tipsDialog;
    @ViewInject(2131231437)
    private TextView tv_change_gesture;

    private void initTipsDialog()
    {
        this.tipsDialog = new TipsDialog.Build(this).setContent(getResources().getString(2131689729)).setDialogClickListener(new AppLockActivity.3(this)).setCancelable(false).build();
    }

    public void destroy() {}

    public void handleBundle(Bundle paramBundle) {}

    public void init()
    {
        setTitleText(getResources().getString(2131690034));
        this.tv_change_gesture.setOnClickListener(new AppLockActivity.1(this));
        if (AppSpUtil.getInstanse().getApplockGestureenable()) {
            this.sb_gesture.setChecked(true);
        }
        for (;;)
        {
            this.sb_gesture.setOnCheckedChangeListener(new AppLockActivity.2(this));
            return;
            this.sb_gesture.setChecked(false);
        }
    }

    protected void onResume()
    {
        super.onResume();
        if (AppSpUtil.getInstanse().getApplockGestureenable())
        {
            this.sb_gesture.setChecked(true);
            return;
        }
        this.sb_gesture.setChecked(false);
    }

    protected void setPresenter()
    {
        this.presenter = new AppLockPresenter(this);
    }
}
