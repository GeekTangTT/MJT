package com.olym.mjt.module.contacts.contactinfo;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.databean.bean.Friend;
import com.olym.mjt.databean.event.LocalContactsChangeEvent;
import com.olym.mjt.glide.GlideUtil;
import com.olym.mjt.module.message.event.CardUIUpdateEvent;
import com.olym.mjt.utils.AvatarHelper;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.utils.EventBusUtil;
import de.hdodenhof.circleimageview.CircleImageView;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427368)
public class ContactsInfoActivity1
        extends BaseTopbarActivity<ContactsInfoPresenter>
        implements IContactsInfoView
{
    public static final String KEY_USER = "user";
    private Friend friend;
    @ViewInject(2131231037)
    private CircleImageView iv_head;
    @ViewInject(2131231125)
    private View ll_sys_call;
    @ViewInject(2131231126)
    private View ll_sys_chat;
    private View.OnClickListener onClickListener = new ContactsInfoActivity1.2(this);
    @ViewInject(2131231460)
    private TextView tv_edit;
    @ViewInject(2131231482)
    private TextView tv_name;
    @ViewInject(2131231488)
    private TextView tv_phone;

    public void destroy()
    {
        EventBusUtil.unregister(this);
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
    public void handleLocalContactsChange(LocalContactsChangeEvent paramLocalContactsChangeEvent)
    {
        if (paramLocalContactsChangeEvent.isDelete()) {
            finish();
        }
    }

    public void init()
    {
        setTitleText(getResources().getString(2131690117));
        if (ChannelUtil.currentChannel == 102)
        {
            this.ll_sys_call.setVisibility(8);
            this.ll_sys_chat.setVisibility(8);
        }
        ((ContactsInfoPresenter)this.presenter).setFriend(this.friend);
        EventBusUtil.register(this);
        GlideUtil.loadUserIcon(this, AvatarHelper.getAvatarUrl(this.friend.getUserId(), this.friend.getDomain(), false), this.iv_head);
        GlideUtil.loadUserIcon(this, AvatarHelper.getAvatarUrl(this.friend.getUserId(), this.friend.getDomain(), true), this.iv_head);
        this.tv_name.setText(this.friend.getShowName());
        this.tv_phone.setText(this.friend.getToTelephone());
        this.ll_sys_call.setOnClickListener(this.onClickListener);
        this.ll_sys_chat.setOnClickListener(this.onClickListener);
        this.tv_edit.setOnClickListener(new ContactsInfoActivity1.1(this));
    }

    protected void setPresenter()
    {
        this.presenter = new ContactsInfoPresenter(this);
    }

    public void transferToChatActivity() {}

    public void updateUi(Friend paramFriend) {}
}

