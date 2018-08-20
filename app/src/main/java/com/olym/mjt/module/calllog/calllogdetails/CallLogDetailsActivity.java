package com.olym.mjt.module.calllog.calllogdetails;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.database.dao.CompanyUserDao;
import com.olym.mjt.database.dao.FriendDao;
import com.olym.mjt.databean.bean.CompanyUser;
import com.olym.mjt.databean.bean.Friend;
import com.olym.mjt.databean.bean.User;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.module.calllog.CallItem;
import com.olym.mjt.module.calllog.CallItem.ExtraItem;
import com.olym.mjt.module.message.event.CloseChatActivityEvent;
import com.olym.mjt.module.message.event.MessageDeleteByFriendEvent;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.utils.DateUtil;
import com.olym.mjt.utils.EventBusUtil;
import com.olym.mjt.utils.ToastUtils;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427361)
public class CallLogDetailsActivity
        extends BaseTopbarActivity<CallLogDetailsPresenter>
        implements ICallLogDetailsView
{
    public static final String KEY_DATA = "data";
    private CallLogDetailsAdapter adapter;
    @ViewInject(2131230751)
    private TextView app_chat;
    @ViewInject(2131230752)
    private TextView app_sip;
    private CallItem callItem;
    private Friend friend;
    @ViewInject(2131231037)
    private CircleImageView iv_head;
    @ViewInject(2131231074)
    private ListView listview;
    @ViewInject(2131231082)
    private View ll_call;
    @ViewInject(2131231086)
    private View ll_chat;
    private View.OnClickListener onClickListener = new CallLogDetailsActivity.1(this);
    @ViewInject(2131231449)
    private TextView tv_date;
    @ViewInject(2131231482)
    private TextView tv_name;
    @ViewInject(2131231488)
    private TextView tv_phone;
    @ViewInject(2131231489)
    private TextView tv_phone_1;

    public void destroy()
    {
        EventBusUtil.unregister(this);
    }

    public void handleBundle(Bundle paramBundle)
    {
        this.callItem = ((CallItem)paramBundle.getParcelable("data"));
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleCloseChatActivityEvent(CloseChatActivityEvent paramCloseChatActivityEvent)
    {
        finish();
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleMessageDeleteByFriend(MessageDeleteByFriendEvent paramMessageDeleteByFriendEvent)
    {
        ToastUtils.showShortToast(getResources().getString(2131690204));
        finish();
    }

    public void init()
    {
        setTitleText(getResources().getString(2131690115));
        setBackText(getResources().getString(2131689884));
        EventBusUtil.register(this);
        String str = this.callItem.getDisplayName();
        Object localObject = str;
        if (str == null) {
            localObject = this.callItem.getName();
        }
        if (ChannelUtil.currentChannel == 102)
        {
            this.app_chat.setText(getResources().getString(2131690108));
            this.app_sip.setText(getResources().getString(2131690112));
        }
        this.tv_name.setText((CharSequence)localObject);
        this.tv_phone_1.setText(getResources().getString(2131689977));
        this.tv_phone.setText(this.callItem.getName());
        this.tv_date.setText(DateUtil.convert_before(this.callItem.getData()));
        if (this.callItem.getExtraItems() == null) {
            this.callItem.setExtraItems(new ArrayList());
        }
        this.callItem.getExtraItems().add(0, new CallItem.ExtraItem(this.callItem.getType(), this.callItem.getData(), this.callItem.getDuration()));
        this.adapter = new CallLogDetailsAdapter(this, this.callItem.getExtraItems());
        this.listview.setAdapter(this.adapter);
        this.friend = FriendDao.getInstance().getFriendFromPhone(MjtApplication.getInstance().getLoginUser().getUserId(), this.callItem.getName());
        if ((this.friend != null) && ((this.friend.getStatus() == 2) || (this.friend.getStatus() == 5)))
        {
            this.ll_chat.setOnClickListener(this.onClickListener);
            this.ll_call.setVisibility(0);
        }
        for (;;)
        {
            if (this.friend == null)
            {
                localObject = CompanyUserDao.getInstance().getCompanyUserFromPhone(this.callItem.getName());
                if ((localObject != null) && (((CompanyUser)localObject).getHidden() == 1)) {
                    this.tv_phone.setText(2131689680);
                }
            }
            this.ll_call.setOnClickListener(this.onClickListener);
            return;
            this.ll_chat.setVisibility(8);
        }
    }

    protected void setPresenter()
    {
        this.presenter = new CallLogDetailsPresenter(this);
    }
}

