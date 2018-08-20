package com.olym.mjt.module.settings.messagestatus;

import android.os.Bundle;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.utils.sharedpreferencesutils.AppSpUtil;
import com.suke.widget.SwitchButton;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427397)
public class MessageStatusActivity
        extends BaseTopbarActivity<MessageStatusPresenter>
        implements IMessageStatusView
{
    @ViewInject(2131231313)
    private SwitchButton sb_reached;
    @ViewInject(2131231314)
    private SwitchButton sb_read;
    @ViewInject(2131231315)
    private SwitchButton sb_sent;

    public void destroy() {}

    public void handleBundle(Bundle paramBundle) {}

    public void init()
    {
        setTitleText(2131689903);
        this.sb_sent.setChecked(AppSpUtil.getInstanse().getMessageStatusSent());
        this.sb_read.setChecked(AppSpUtil.getInstanse().getMessageStatusRead());
        this.sb_reached.setChecked(AppSpUtil.getInstanse().getMessageStatusReached());
        this.sb_reached.setOnCheckedChangeListener(new MessageStatusActivity.1(this));
        this.sb_read.setOnCheckedChangeListener(new MessageStatusActivity.2(this));
        this.sb_sent.setOnCheckedChangeListener(new MessageStatusActivity.3(this));
    }

    protected void setPresenter()
    {
        this.presenter = new MessageStatusPresenter(this);
    }
}

