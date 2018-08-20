package com.olym.mjt.module.message.sms.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageButton;
import com.olym.mjt.base.activity.BasePresenterActivity;
import com.olym.mjt.module.message.sms.event.SmsLoadDataFromDBEvent;
import com.olym.mjt.utils.EventBusUtil;
import com.olym.mjt.utils.StatusBarUtil;
import com.olym.mjt.widget.swipeview.SwipeMenuListView;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427509)
public class EncryptSmsActivity
        extends BasePresenterActivity<SmsPresenter>
        implements ISmsView
{
    private Handler handler = new Handler(Looper.getMainLooper());
    @ViewInject(2131231369)
    private SwipeMenuListView listView;
    private SmsAdapter smsAdapter;
    private long stopTimes = -1L;
    @ViewInject(2131231576)
    private ImageButton write_sms;

    public void destroy()
    {
        EventBusUtil.unregister(this);
    }

    public void handleBundle(Bundle paramBundle) {}

    @Subscribe(sticky=true, threadMode=ThreadMode.MAIN)
    public void handlerSmsLoadDataFromDBEvent(SmsLoadDataFromDBEvent paramSmsLoadDataFromDBEvent)
    {
        ((SmsPresenter)this.presenter).loadDataFromDB();
    }

    public void init()
    {
        EventBusUtil.register(this);
        StatusBarUtil.setStatusBar(this);
        this.smsAdapter = new SmsAdapter(this, ((SmsPresenter)this.presenter).getData());
        EncryptSmsActivity.2 local2 = new EncryptSmsActivity.2(this);
        this.listView.setMenuCreator(local2);
        this.listView.setOnMenuItemClickListener(new EncryptSmsActivity.3(this));
        this.listView.setAdapter(this.smsAdapter);
        this.listView.setOnItemClickListener(new EncryptSmsActivity.4(this));
        this.listView.setDividerHeight(0);
        this.write_sms.setOnClickListener(new EncryptSmsActivity.5(this));
    }

    protected void setPresenter()
    {
        this.presenter = new SmsPresenter(this);
    }

    public void updateApdater()
    {
        if (this.smsAdapter != null) {
            this.handler.post(new EncryptSmsActivity.1(this));
        }
    }
}
