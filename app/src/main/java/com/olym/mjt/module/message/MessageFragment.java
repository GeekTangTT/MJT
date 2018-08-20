package com.olym.mjt.module.message;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.olym.mjt.base.fragment.BasePresenterFragment;
import com.olym.mjt.databean.bean.Friend;
import com.olym.mjt.databean.bean.MucRoomMember;
import com.olym.mjt.databean.event.ShowNetBarEvent;
import com.olym.mjt.databean.event.ShowNotificationEvent;
import com.olym.mjt.databean.event.TransportStateEvent;
import com.olym.mjt.module.message.event.ChangeMessageFragmentTitleEvent;
import com.olym.mjt.module.message.event.ClearChatMessageEvent;
import com.olym.mjt.module.message.event.DeleteRoomEvent;
import com.olym.mjt.module.message.event.MessageMsgUiUpdateEvent;
import com.olym.mjt.module.message.event.MsgNumRefreshEvent;
import com.olym.mjt.module.message.event.MsgNumUpdateEvent;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.utils.EventBusUtil;
import com.olym.mjt.utils.StringUtils;
import com.olym.mjt.widget.MorePopupWindow;
import com.olym.mjt.widget.swipeview.SwipeMenuListView;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427506)
public class MessageFragment
        extends BasePresenterFragment<MessagePresenter>
        implements IMessageView
{
    private int count;
    private ArrayList<Friend> dates;
    private Handler handler = new Handler(Looper.getMainLooper());
    @ViewInject(2131231074)
    private SwipeMenuListView listview;
    @ViewInject(2131231112)
    private View ll_netnull;
    private String mLoginUserId;
    private List<MucRoomMember> mMembers;
    private Friend mRoom;
    private MessageAdapter messageAdapter;
    @ViewInject(2131231170)
    private TextView message_title;
    private MorePopupWindow morePopupWindow;
    private int type;
    private int unReadName;
    @ViewInject(2131231548)
    private ImageView v_more;

    public void destroy()
    {
        EventBusUtil.unregister(this);
    }

    public void handleArguments() {}

    @Subscribe(sticky=true, threadMode=ThreadMode.MAIN)
    public void handleChangeMessageFragmentTitleEvent(ChangeMessageFragmentTitleEvent paramChangeMessageFragmentTitleEvent)
    {
        boolean bool = paramChangeMessageFragmentTitleEvent.isConnected();
        if (this.message_title != null)
        {
            if (!bool) {
                break label77;
            }
            Applog.systemOut("-----IM������-----");
            Applog.info("-----IM������-----");
            if (ChannelUtil.currentChannel != 102) {
                break label65;
            }
            this.message_title.setText(2131690074);
        }
        for (;;)
        {
            this.ll_netnull.setVisibility(8);
            ShowNotificationEvent.post(new ShowNotificationEvent(3, 1));
            return;
            label65:
            this.message_title.setText(2131690073);
        }
        label77:
        Applog.systemOut("-----IM������-----");
        Applog.info("-----IM������-----");
        if (ChannelUtil.currentChannel == 102) {
            this.message_title.setText(2131690072);
        }
        for (;;)
        {
            ShowNotificationEvent.post(new ShowNotificationEvent(3, -1));
            return;
            this.message_title.setText(2131690071);
        }
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleClearChatMessageEvent(ClearChatMessageEvent paramClearChatMessageEvent)
    {
        ((MessagePresenter)this.presenter).loadDataFromDB();
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleDeleteRoomEvent(DeleteRoomEvent paramDeleteRoomEvent)
    {
        this.mLoginUserId = paramDeleteRoomEvent.getUserid();
        this.mRoom = paramDeleteRoomEvent.getmUser();
        this.mMembers = paramDeleteRoomEvent.getMembers();
        ((MessagePresenter)this.presenter).deleteRoom(this.mLoginUserId, this.mRoom, this.mMembers);
    }

    @Subscribe(sticky=true, threadMode=ThreadMode.MAIN)
    public void handleMessgageMsgUiUpdateEvent(MessageMsgUiUpdateEvent paramMessageMsgUiUpdateEvent)
    {
        Applog.systemOut("MessageFragment:MessageMsgUiUpdateEvent");
        ((MessagePresenter)this.presenter).loadDataFromDB();
    }

    @Subscribe(sticky=true, threadMode=ThreadMode.MAIN)
    public void handleMsgNumUpdateEvent(MsgNumUpdateEvent paramMsgNumUpdateEvent)
    {
        this.type = paramMsgNumUpdateEvent.getType();
        this.count = paramMsgNumUpdateEvent.getCount();
    }

    @Subscribe(sticky=true, threadMode=ThreadMode.MAIN)
    public void handleShowNetBarEvent(ShowNetBarEvent paramShowNetBarEvent)
    {
        if (paramShowNetBarEvent.isShow())
        {
            this.ll_netnull.setVisibility(0);
            return;
        }
        this.ll_netnull.setVisibility(8);
    }

    @Subscribe(sticky=true, threadMode=ThreadMode.MAIN)
    public void handleTransportState(TransportStateEvent paramTransportStateEvent)
    {
        if ((paramTransportStateEvent.getState() == 2) || (paramTransportStateEvent.getState() == 3))
        {
            Applog.systemOut("-----IM������-----");
            Applog.info("-----IM������-----");
            if (ChannelUtil.currentChannel != 102) {
                break label56;
            }
            this.message_title.setText(2131690072);
        }
        for (;;)
        {
            ShowNotificationEvent.post(new ShowNotificationEvent(3, -1));
            return;
            label56:
            this.message_title.setText(2131690071);
        }
    }

    @Subscribe(sticky=true, threadMode=ThreadMode.MAIN)
    public void handlerMsgRefreshEvent(MsgNumRefreshEvent paramMsgNumRefreshEvent)
    {
        Applog.systemOut("MessageFragment:MsgNumRefreshEvent");
        ((MessagePresenter)this.presenter).loadDataFromDB();
    }

    public void init()
    {
        EventBusUtil.register(this);
        this.dates = new ArrayList();
        this.morePopupWindow = new MorePopupWindow(this.activity);
        if (this.messageAdapter == null)
        {
            ((MessagePresenter)this.presenter).loadDataFromDB();
            this.messageAdapter = new MessageAdapter(this.activity, ((MessagePresenter)this.presenter).getDatas());
        }
        MessageFragment.1 local1 = new MessageFragment.1(this);
        this.listview.setMenuCreator(local1);
        this.listview.setOnMenuItemClickListener(new MessageFragment.2(this));
        this.listview.setAdapter(this.messageAdapter);
        this.listview.setOnItemClickListener(new MessageFragment.3(this));
        this.listview.setDividerHeight(0);
        this.v_more.setOnClickListener(new MessageFragment.4(this));
        this.ll_netnull.setOnClickListener(new MessageFragment.5(this));
        this.messageAdapter.setSearchViewClick(new MessageFragment.6(this));
    }

    public void loadDatas()
    {
        ((MessagePresenter)this.presenter).loadData();
    }

    public void onHiddenChanged(boolean paramBoolean)
    {
        super.onHiddenChanged(paramBoolean);
        if (!paramBoolean) {
            ShowNotificationEvent.post(new ShowNotificationEvent(2));
        }
    }

    public void onStart()
    {
        super.onStart();
        ShowNotificationEvent.post(new ShowNotificationEvent(2));
    }

    public void refreshUnread(Friend paramFriend)
    {
        int j = ((MessagePresenter)this.presenter).getDatas().size();
        int i = 0;
        while (i < j)
        {
            if (StringUtils.strEquals(((Friend)((MessagePresenter)this.presenter).getDatas().get(i)).getUserId(), paramFriend.getUserId())) {
                ((Friend)((MessagePresenter)this.presenter).getDatas().get(i)).setUnReadNum(paramFriend.getUnReadNum());
            }
            i += 1;
        }
        if (this.messageAdapter != null) {
            this.messageAdapter.notifyDataSetChanged();
        }
    }

    protected void setPresenter()
    {
        this.presenter = new MessagePresenter(this);
    }

    public void updateAdapter()
    {
        if (this.messageAdapter != null) {
            this.handler.post(new MessageFragment.7(this));
        }
    }
}

