package com.olym.mjt.module.contacts.contactinfo;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.databean.bean.Friend;
import com.olym.mjt.databean.event.LocalContactsChangeEvent;
import com.olym.mjt.databean.event.UpdateFriendInfoEvent;
import com.olym.mjt.glide.GlideUtil;
import com.olym.mjt.module.calllog.CallItem;
import com.olym.mjt.module.message.chat.MJTChatActivity;
import com.olym.mjt.module.message.event.CardUIUpdateEvent;
import com.olym.mjt.module.message.event.CloseChatActivityEvent;
import com.olym.mjt.module.message.event.MessageDeleteByFriendEvent;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.AvatarHelper;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.utils.EventBusUtil;
import com.olym.mjt.utils.ToastUtils;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427367)
public class ContactsInfoActivity
        extends BaseTopbarActivity<ContactsInfoPresenter>
        implements IContactsInfoView
{
    public static final String KEY_USER = "user";
    private static final int LOADER_ID = 1009;
    private ContactsInfoAdapter adapter;
    @ViewInject(2131230751)
    private TextView app_chat;
    @ViewInject(2131230752)
    private TextView app_sip;
    private ArrayList<CallItem> datas = new ArrayList();
    private Friend friend;
    @ViewInject(2131231037)
    private CircleImageView iv_head;
    @ViewInject(2131231074)
    private ListView listview;
    @ViewInject(2131231082)
    private View ll_call;
    @ViewInject(2131231122)
    private View ll_sip_call;
    @ViewInject(2131231123)
    private View ll_sip_chat;
    @ViewInject(2131231125)
    private View ll_sys_call;
    @ViewInject(2131231126)
    private View ll_sys_chat;
    private View.OnClickListener onClickListener = new ContactsInfoActivity.4(this);
    @ViewInject(2131231428)
    private TextView tv1;
    @ViewInject(2131231460)
    private TextView tv_edit;
    @ViewInject(2131231482)
    private TextView tv_name;
    @ViewInject(2131231488)
    private TextView tv_phone;

    public void destroy()
    {
        EventBusUtil.unregister(this);
        getSupportLoaderManager().destroyLoader(1009);
    }

    public void handleBundle(Bundle paramBundle)
    {
        this.friend = ((Friend)paramBundle.getSerializable("user"));
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleCardUIUpdate(CardUIUpdateEvent paramCardUIUpdateEvent)
    {
        if ((paramCardUIUpdateEvent.getPhone() != null) && (!this.friend.getToTelephone().equals(paramCardUIUpdateEvent.getPhone()))) {
            finish();
        }
        while (paramCardUIUpdateEvent.getName() == null) {
            return;
        }
        this.tv_name.setText(paramCardUIUpdateEvent.getName());
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleCloseChatActivityEvent(CloseChatActivityEvent paramCloseChatActivityEvent)
    {
        finish();
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleLocalContactsChange(LocalContactsChangeEvent paramLocalContactsChangeEvent)
    {
        if (paramLocalContactsChangeEvent.isDelete()) {
            finish();
        }
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleMessageDeleteByFriend(MessageDeleteByFriendEvent paramMessageDeleteByFriendEvent)
    {
        ToastUtils.showShortToast(2131690204);
        finish();
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleUpdateFriendInfo(UpdateFriendInfoEvent paramUpdateFriendInfoEvent)
    {
        paramUpdateFriendInfoEvent = paramUpdateFriendInfoEvent.getFriend();
        Applog.systemOut("-----handleUpdateFriendInfo----- " + paramUpdateFriendInfoEvent);
        if ((paramUpdateFriendInfoEvent.getUserId().equals(this.friend.getUserId())) && (paramUpdateFriendInfoEvent.getDomain().equals(this.friend.getDomain())))
        {
            Applog.systemOut("------------- " + this.friend.getShowName());
            this.tv_name.setText(paramUpdateFriendInfoEvent.getShowName());
        }
    }

    public void init()
    {
        setTitleText(getResources().getString(2131690117));
        EventBusUtil.register(this);
        ((ContactsInfoPresenter)this.presenter).setFriend(this.friend);
        if (ChannelUtil.currentChannel == 102)
        {
            this.app_chat.setText(getResources().getString(2131690108));
            this.app_sip.setText(getResources().getString(2131690112));
            this.ll_sys_call.setVisibility(8);
            this.ll_sys_chat.setVisibility(8);
        }
        GlideUtil.loadUserIcon(this, AvatarHelper.getAvatarUrl(this.friend.getUserId(), this.friend.getDomain(), false), this.iv_head);
        new Handler().postDelayed(new ContactsInfoActivity.1(this), 1000L);
        String str2 = this.friend.getDomain();
        String str1 = str2;
        if (str2 != null)
        {
            str1 = str2;
            if (str2.contains(".")) {
                str1 = str2.split("[.]")[0];
            }
        }
        this.tv1.setText(str1);
        this.tv_name.setText(this.friend.getShowName());
        this.tv_phone.setText(this.friend.getToTelephone());
        this.ll_sip_call.setOnClickListener(this.onClickListener);
        this.ll_sip_chat.setOnClickListener(this.onClickListener);
        this.ll_sys_call.setOnClickListener(this.onClickListener);
        this.ll_sys_chat.setOnClickListener(this.onClickListener);
        if ((this.friend.getStatus() == 5) || (this.friend.getStatus() == 2) || (this.friend.getStatus() == -1)) {
            this.ll_sip_chat.setVisibility(0);
        }
        if (ChannelUtil.currentChannel == 102)
        {
            this.ll_sys_chat.setVisibility(8);
            this.ll_sys_call.setVisibility(8);
        }
        for (;;)
        {
            this.tv_edit.setVisibility(0);
            this.tv_edit.setOnClickListener(new ContactsInfoActivity.2(this));
            this.adapter = new ContactsInfoAdapter(this, this.datas);
            this.listview.setAdapter(this.adapter);
            getSupportLoaderManager().initLoader(1009, null, new ContactsInfoActivity.3(this));
            return;
            this.ll_sys_chat.setVisibility(0);
            this.ll_sys_call.setVisibility(0);
        }
    }

    protected void onResume()
    {
        super.onResume();
    }

    protected void setPresenter()
    {
        this.presenter = new ContactsInfoPresenter(this);
    }

    public void transferToChatActivity()
    {
        Intent localIntent = new Intent(this, MJTChatActivity.class);
        Bundle localBundle = new Bundle();
        localBundle.putSerializable("friend", this.friend);
        localIntent.putExtras(localBundle);
        startActivity(localIntent);
    }

    public void updateUi(Friend paramFriend)
    {
        this.tv_name.setText(paramFriend.getShowName());
    }
}

