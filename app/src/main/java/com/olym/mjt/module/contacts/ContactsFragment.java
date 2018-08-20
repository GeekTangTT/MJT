package com.olym.mjt.module.contacts;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.olym.mjt.base.fragment.BasePresenterFragment;
import com.olym.mjt.databean.bean.Friend;
import com.olym.mjt.databean.event.LocalContactsLoadEvent;
import com.olym.mjt.databean.event.NewFriendNumEvent;
import com.olym.mjt.databean.event.UpdateFriendInfoEvent;
import com.olym.mjt.databean.event.UpdateNewFriendUnreadEvent;
import com.olym.mjt.im.IMService;
import com.olym.mjt.module.message.event.CardUIUpdateEvent;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.utils.EventBusUtil;
import com.olym.mjt.utils.KeyboardHideUtil;
import com.olym.mjt.widget.sortlist.SideBar;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427503)
public class ContactsFragment
        extends BasePresenterFragment<ContactsPresenter>
        implements IContactsView
{
    ServiceConnection IMServiceconn = new ContactsFragment.6(this);
    private ContactsAdapter adapter;
    @ViewInject(2131230883)
    private TextView contact_title;
    private Handler handler = new Handler(Looper.getMainLooper());
    @ViewInject(2131231074)
    private PullToRefreshListView listView;
    private IMService mImService;
    @ViewInject(2131231362)
    private SideBar sideBar;
    @ViewInject(2131231548)
    private View v_more;

    public void destroy()
    {
        EventBusUtil.unregister(this);
        getContext().unbindService(this.IMServiceconn);
    }

    public void handleArguments() {}

    @Subscribe(sticky=true, threadMode=ThreadMode.MAIN)
    public void handleCardUIUpdateEvent(CardUIUpdateEvent paramCardUIUpdateEvent)
    {
        Applog.systemOut("---handleCardUIUpdateEvent--������������---");
        ((ContactsPresenter)this.presenter).loadDataFromDB();
        LocalContactsLoadEvent.post(new LocalContactsLoadEvent());
    }

    @Subscribe(sticky=true, threadMode=ThreadMode.MAIN)
    public void handleUnreadUpdate(UpdateNewFriendUnreadEvent paramUpdateNewFriendUnreadEvent)
    {
        ((ContactsPresenter)this.presenter).loadDataFromDB();
    }

    @Subscribe(sticky=true, threadMode=ThreadMode.MAIN)
    public void handleUpdateFriendInfo(UpdateFriendInfoEvent paramUpdateFriendInfoEvent)
    {
        Applog.systemOut("----handleUpdateFriendInfo111------ " + paramUpdateFriendInfoEvent.getFriend());
        paramUpdateFriendInfoEvent = paramUpdateFriendInfoEvent.getFriend();
        if (paramUpdateFriendInfoEvent == null) {
            ((ContactsPresenter)this.presenter).loadDataFromDB();
        }
        for (;;)
        {
            LocalContactsLoadEvent.post(new LocalContactsLoadEvent());
            return;
            if (paramUpdateFriendInfoEvent.getRoomFlag() == 0) {
                ((ContactsPresenter)this.presenter).updateDatas(paramUpdateFriendInfoEvent);
            }
        }
    }

    @Subscribe(sticky=true, threadMode=ThreadMode.MAIN)
    public void handlerNewFriendNumUpdateEvent(NewFriendNumEvent paramNewFriendNumEvent)
    {
        ((ContactsPresenter)this.presenter).loadDataFromDB();
    }

    public void init()
    {
        KeyboardHideUtil.init(this.activity);
        EventBusUtil.register(this);
        if (ChannelUtil.currentChannel == 102)
        {
            this.contact_title.setText(getResources().getString(2131690110));
            if (this.adapter == null) {
                this.adapter = new ContactsAdapter(this.activity, ((ContactsPresenter)this.presenter).getDatas(), (ContactsPresenter)this.presenter);
            }
            Intent localIntent = new Intent(getContext(), IMService.class);
            getContext().bindService(localIntent, this.IMServiceconn, 1);
            this.listView.setAdapter(this.adapter);
            ((ListView)this.listView.getRefreshableView()).setDividerHeight(0);
            this.listView.setOnRefreshListener(new ContactsFragment.1(this));
            this.listView.setOnScrollListener(new ContactsFragment.2(this));
            this.sideBar.setOnTouchingLetterChangedListener(new ContactsFragment.3(this));
            this.v_more.setOnClickListener(new ContactsFragment.4(this));
            if (ChannelUtil.currentChannel != 102) {
                break label244;
            }
            this.v_more.setVisibility(8);
        }
        for (;;)
        {
            this.handler.postDelayed(new ContactsFragment.5(this), 250L);
            return;
            this.contact_title.setText(getResources().getString(2131690109));
            break;
            label244:
            this.v_more.setVisibility(0);
        }
    }

    public void onAttach(Context paramContext)
    {
        super.onAttach(paramContext);
    }

    @Nullable
    public View onCreateView(LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle)
    {
        return super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    }

    public void onDestroyView()
    {
        super.onDestroyView();
    }

    public void onDetach()
    {
        super.onDetach();
    }

    protected void setPresenter()
    {
        this.presenter = new ContactsPresenter(this);
    }

    public void updateAdapter()
    {
        this.handler.post(new ContactsFragment.7(this));
    }
}
