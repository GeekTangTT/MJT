package com.olym.mjt.module.message.newfriend;

import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Bundle;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.database.dao.FriendDao;
import com.olym.mjt.database.dao.NewFriendDao;
import com.olym.mjt.databean.bean.NewFriendMessage;
import com.olym.mjt.databean.bean.User;
import com.olym.mjt.databean.event.UpdateNewFriendUnreadEvent;
import com.olym.mjt.im.IMService;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.module.message.event.MessageDeleteByFriendEvent;
import com.olym.mjt.module.message.listener.ListenerManager;
import com.olym.mjt.module.message.listener.NewFriendListener;
import com.olym.mjt.utils.EventBusUtil;
import com.olym.mjt.widget.swipeview.SwipeMenuListView;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427404)
public class MJTNewFriendActivity
        extends BaseTopbarActivity<MJTNewFriendPresenter>
        implements IMJTNewFriendView
{
    private List<NewFriendMessage> allDate;
    private IMService imService;
    private NewFriendAdapter mNewFriendAdapter;
    NewFriendAdapter.NewFriendActionListener newFriendActionListener = new MJTNewFriendActivity.5(this);
    NewFriendListener newFriendListener = new MJTNewFriendActivity.4(this);
    @ViewInject(2131231209)
    private SwipeMenuListView newfriendListview;
    ServiceConnection serviceConnection = new MJTNewFriendActivity.3(this);

    public void destroy()
    {
        unbindService(this.serviceConnection);
        EventBusUtil.unregister(this);
        ListenerManager.getInstance().removeNewFriendListener(this.newFriendListener);
    }

    public void handleBundle(Bundle paramBundle) {}

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleMessageDeleteByFriend(MessageDeleteByFriendEvent paramMessageDeleteByFriendEvent)
    {
        finish();
    }

    public void init()
    {
        setBackText(getResources().getString(2131689620));
        setTitleText(getResources().getString(2131689943));
        this.allDate = new ArrayList();
        EventBusUtil.register(this);
        this.mNewFriendAdapter = new NewFriendAdapter(this, this.allDate, this.newFriendActionListener);
        this.newfriendListview.setAdapter(this.mNewFriendAdapter);
        ((MJTNewFriendPresenter)this.presenter).loadDate();
        MJTNewFriendActivity.1 local1 = new MJTNewFriendActivity.1(this);
        this.newfriendListview.setMenuCreator(local1);
        this.newfriendListview.setOnMenuItemClickListener(new MJTNewFriendActivity.2(this));
        FriendDao.getInstance().markUserMessageRead(MjtApplication.getInstance().getLoginUser().getUserId(), "10001");
        NewFriendDao.getInstance().markNewFriendRead(MjtApplication.getInstance().getLoginUser().getUserId());
        NewFriendDao.getInstance().markUserMessageRead(MjtApplication.getInstance().getLoginUser().getUserId());
        ListenerManager.getInstance().addNewFriendListener(this.newFriendListener);
        UpdateNewFriendUnreadEvent.post(new UpdateNewFriendUnreadEvent());
        bindService(new Intent(this, IMService.class), this.serviceConnection, 1);
    }

    public void notifyLoaded(List<NewFriendMessage> paramList) {}

    protected void setPresenter()
    {
        this.presenter = new MJTNewFriendPresenter(this, this);
    }

    public void updateAdapter()
    {
        runOnUiThread(new MJTNewFriendActivity.6(this));
    }
}

