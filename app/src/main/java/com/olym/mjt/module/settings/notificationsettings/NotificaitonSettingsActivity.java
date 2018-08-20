package com.olym.mjt.module.settings.notificationsettings;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.databean.bean.SoundBean;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.utils.sharedpreferencesutils.AppSpUtil;
import com.suke.widget.SwitchButton;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427409)
public class NotificaitonSettingsActivity
        extends BaseTopbarActivity<NotificaitonSettingsPresenter>
        implements INotificationSettingsView
{
    @ViewInject(2131231114)
    private LinearLayout ll_notificaiton;
    @ViewInject(2131231120)
    private LinearLayout ll_ringtone;
    @ViewInject(2131231128)
    private View ll_top;
    @ViewInject(2131231316)
    private SwitchButton sb_sound;
    @ViewInject(2131231317)
    private SwitchButton sb_vibrate;
    @ViewInject(2131231484)
    private TextView tv_notification;
    @ViewInject(2131231498)
    private TextView tv_ringtone;

    public void destroy() {}

    public void handleBundle(Bundle paramBundle) {}

    public void init()
    {
        setTitleText(getResources().getString(2131690055));
        if (ChannelUtil.currentChannel == 106)
        {
            this.ll_top.setVisibility(8);
            if (!AppSpUtil.getInstanse().getSoundEnable()) {
                break label136;
            }
            this.sb_sound.setChecked(true);
            label47:
            if (!AppSpUtil.getInstanse().getVibrateEnable()) {
                break label147;
            }
            this.sb_vibrate.setChecked(true);
        }
        for (;;)
        {
            this.sb_sound.setOnCheckedChangeListener(new NotificaitonSettingsActivity.1(this));
            this.sb_vibrate.setOnCheckedChangeListener(new NotificaitonSettingsActivity.2(this));
            this.ll_notificaiton.setOnClickListener(new NotificaitonSettingsActivity.3(this));
            this.ll_ringtone.setOnClickListener(new NotificaitonSettingsActivity.4(this));
            return;
            this.ll_top.setVisibility(0);
            break;
            label136:
            this.sb_sound.setChecked(false);
            break label47;
            label147:
            this.sb_vibrate.setChecked(false);
        }
    }

    protected void onResume()
    {
        super.onResume();
        this.tv_ringtone.setText(AppSpUtil.getInstanse().getRingTone().getTitle());
        this.tv_notification.setText(AppSpUtil.getInstanse().getNotificationSound().getTitle());
    }

    protected void setPresenter()
    {
        this.presenter = new NotificaitonSettingsPresenter(this);
    }
}

