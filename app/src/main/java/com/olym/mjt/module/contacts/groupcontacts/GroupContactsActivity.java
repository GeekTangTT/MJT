package com.olym.mjt.module.contacts.groupcontacts;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.databean.bean.Friend;
import com.olym.mjt.module.message.event.UpdateGroupContactsEvent;
import com.olym.mjt.utils.EventBusUtil;
import java.util.ArrayList;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427383)
public class GroupContactsActivity
        extends BaseTopbarActivity<GroupContactsPresenter>
        implements IGroupContactsView
{
    private GroupContactsAdapter adapter;
    private ArrayList<Friend> datas = new ArrayList();
    @ViewInject(2131231074)
    private PullToRefreshListView listview;
    @ViewInject(2131231525)
    private View v_add_group;

    public void destroy()
    {
        EventBusUtil.unregister(this);
    }

    public void handleBundle(Bundle paramBundle) {}

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handlerUpdateGroupContactsEvent(UpdateGroupContactsEvent paramUpdateGroupContactsEvent)
    {
        ((GroupContactsPresenter)this.presenter).loadDataFromDB();
    }

    public void init()
    {
        setTitleText(getResources().getString(2131689916));
        setBackText(getResources().getString(2131690109));
        EventBusUtil.register(this);
        this.datas = ((GroupContactsPresenter)this.presenter).getDatas();
        ((ListView)this.listview.getRefreshableView()).setDividerHeight(0);
        this.listview.setOnRefreshListener(new GroupContactsActivity.1(this));
        this.listview.setOnScrollListener(new GroupContactsActivity.2(this));
        this.adapter = new GroupContactsAdapter(this, this.datas);
        this.listview.setAdapter(this.adapter);
        this.v_add_group.setOnClickListener(new GroupContactsActivity.3(this));
    }

    protected void setPresenter()
    {
        this.presenter = new GroupContactsPresenter(this);
    }

    public void updateAdapter()
    {
        if (this.adapter != null) {
            runOnUiThread(new GroupContactsActivity.4(this));
        }
    }
}
