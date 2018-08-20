package com.olym.mjt.module.contacts.friendinfonew;

import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.databean.bean.Friend;
import com.olym.mjt.glide.GlideUtil;
import com.olym.mjt.im.IMService;
import com.olym.mjt.module.calllog.CallItem;
import com.olym.mjt.module.message.chat.MJTChatActivity;
import com.olym.mjt.module.message.event.CloseChatActivityEvent;
import com.olym.mjt.module.message.event.MessageDeleteByFriendEvent;
import com.olym.mjt.utils.AvatarHelper;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.utils.EventBusUtil;
import com.olym.mjt.utils.ToastUtils;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;
import com.olym.mjt.widget.dialog.InputDialog;
import com.olym.mjt.widget.dialog.InputDialog.Build;
import com.olym.mjt.widget.dialog.LoadingDialog;
import com.olym.mjt.widget.dialog.TipsDialog;
import com.olym.mjt.widget.dialog.TipsDialog.Build;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427381)
public class FriendInfoNewActivity
        extends BaseTopbarActivity<FriendInfoNewPresenter>
        implements IFriendInfoNewView
{
    public static final String KEY_DATA = "data";
    public static final String KEY_ROOMID = "room_id";
    private static final int LOADER_ID = 1010;
    private static final int MODE_FRIEND = 100;
    private static final int MODE_LOCAL = 102;
    private static final int MODE_REGISTED = 101;
    private ServiceConnection ImSerViceConn = new FriendInfoNewActivity.3(this);
    private FriendInfoNewAdapter adapter;
    private ArrayList<CallItem> datas = new ArrayList();
    private TipsDialog deleteTipsDialog;
    private Friend friend;
    private InputDialog inputDialog;
    @ViewInject(2131231037)
    private CircleImageView iv_head;
    @ViewInject(2131231057)
    private ImageView iv_sip;
    @ViewInject(2131231060)
    private ImageView iv_xmpp;
    @ViewInject(2131231074)
    private ListView listview;
    @ViewInject(2131231084)
    private View ll_call_records;
    @ViewInject(2131231115)
    private View ll_phone;
    @ViewInject(2131231119)
    private View ll_remark;
    @ViewInject(2131231122)
    private View ll_sip_call;
    @ViewInject(2131231125)
    private View ll_sys_call;
    @ViewInject(2131231126)
    private View ll_sys_chat;
    @ViewInject(2131231133)
    private View ll_xmpp_chat;
    private LoadingDialog loadingDialog;
    private IMService mImService;
    private int mode = 102;
    private View.OnClickListener onClickListener = new FriendInfoNewActivity.6(this);
    private String roomId;
    @ViewInject(2131231432)
    private TextView tv_action_1;
    @ViewInject(2131231433)
    private TextView tv_action_2;
    @ViewInject(2131231480)
    private TextView tv_more;
    @ViewInject(2131231482)
    private TextView tv_name;
    @ViewInject(2131231488)
    private TextView tv_phone;
    @ViewInject(2131231495)
    private TextView tv_remark;
    @ViewInject(2131231505)
    private TextView tv_sip;
    @ViewInject(2131231516)
    private TextView tv_xmpp;
    @ViewInject(2131231538)
    private View v_line_call;

    private void enableSip(boolean paramBoolean)
    {
        this.tv_sip.setEnabled(paramBoolean);
        this.iv_sip.setEnabled(paramBoolean);
        this.ll_sip_call.setEnabled(paramBoolean);
    }

    private void enableXmpp(boolean paramBoolean)
    {
        this.tv_xmpp.setEnabled(paramBoolean);
        this.iv_xmpp.setEnabled(paramBoolean);
        this.ll_xmpp_chat.setEnabled(paramBoolean);
    }

    private void initDeleteTipsDialog()
    {
        if (this.deleteTipsDialog == null) {
            this.deleteTipsDialog = new TipsDialog.Build(this).setContent(getResources().getString(2131689720)).setDialogClickListener(new FriendInfoNewActivity.2(this)).build();
        }
    }

    private void initInputDialog()
    {
        if (this.inputDialog == null) {
            this.inputDialog = new InputDialog.Build(this).setTitle(getResources().getString(2131689651)).setContentLimit(25).setContent(this.friend.getRemarkName()).setDialogClickListener(new FriendInfoNewActivity.1(this)).build();
        }
    }

    private void setMode(int paramInt)
    {
        switch (paramInt)
        {
            default:
            case 100:
            case 101:
                do
                {
                    return;
                    enableXmpp(true);
                    enableSip(true);
                    this.tv_action_1.setVisibility(8);
                    this.tv_action_2.setVisibility(8);
                    this.tv_more.setVisibility(0);
                    return;
                    enableXmpp(false);
                    enableSip(true);
                    this.tv_action_1.setText(2131689583);
                    this.tv_action_2.setVisibility(8);
                    this.tv_more.setVisibility(8);
                } while (ChannelUtil.currentChannel != 102);
                this.tv_action_1.setVisibility(8);
                return;
        }
        enableXmpp(false);
        enableSip(false);
        if (UserSpUtil.getInstanse().getVip() == 1) {
            this.tv_action_1.setVisibility(0);
        }
        for (;;)
        {
            this.tv_action_1.setText(2131689861);
            this.tv_action_2.setText(2131689704);
            this.tv_more.setVisibility(8);
            return;
            this.tv_action_1.setVisibility(8);
        }
    }

    private void showCallRecorders(boolean paramBoolean)
    {
        if (paramBoolean)
        {
            this.ll_call_records.setVisibility(0);
            this.v_line_call.setVisibility(8);
            return;
        }
        this.ll_call_records.setVisibility(8);
        this.v_line_call.setVisibility(0);
    }

    public void deleteFriendSuccess() {}

    public void destroy()
    {
        unbindService(this.ImSerViceConn);
        EventBusUtil.unregister(this);
        getSupportLoaderManager().destroyLoader(1010);
        if (this.loadingDialog.isShowing()) {
            this.loadingDialog.dismiss();
        }
    }

    public void handleBundle(Bundle paramBundle)
    {
        this.friend = ((Friend)paramBundle.getSerializable("data"));
        this.roomId = paramBundle.getString("room_id", "");
        if (this.friend == null) {
            finish();
        }
        if (!this.friend.getUserId().equals("10006"))
        {
            if ((this.friend.getStatus() == 5) || (this.friend.getStatus() == 2) || (this.friend.getStatus() == -1))
            {
                this.mode = 100;
                return;
            }
            this.mode = 101;
            return;
        }
        this.mode = 102;
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleCloseChatActivityEvent(CloseChatActivityEvent paramCloseChatActivityEvent)
    {
        finish();
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleMessageDeleteByFriend(MessageDeleteByFriendEvent paramMessageDeleteByFriendEvent)
    {
        ToastUtils.showShortToast(2131690204);
        finish();
    }

    public void hideLoading() {}

    public void init()
    {
        setTitleText(2131690118);
        EventBusUtil.register(this);
        bindService(new Intent(this, IMService.class), this.ImSerViceConn, 1);
        this.loadingDialog = new LoadingDialog(this);
        ((FriendInfoNewPresenter)this.presenter).setFriend(this.friend, this.roomId);
        showCallRecorders(false);
        GlideUtil.loadUserIcon(this, AvatarHelper.getAvatarUrl(this.friend.getUserId(), this.friend.getDomain(), false), this.iv_head);
        new Handler().postDelayed(new FriendInfoNewActivity.4(this), 1000L);
        this.tv_name.setText(this.friend.getShowName());
        this.tv_remark.setText(this.friend.getRemarkName());
        this.tv_phone.setText(this.friend.getToTelephone());
        this.adapter = new FriendInfoNewAdapter(this, this.datas);
        this.listview.setAdapter(this.adapter);
        setMode(this.mode);
        getSupportLoaderManager().initLoader(1010, null, new FriendInfoNewActivity.5(this));
        this.tv_more.setOnClickListener(this.onClickListener);
        this.ll_sip_call.setOnClickListener(this.onClickListener);
        this.ll_xmpp_chat.setOnClickListener(this.onClickListener);
        this.ll_sys_call.setOnClickListener(this.onClickListener);
        this.ll_sys_chat.setOnClickListener(this.onClickListener);
        this.tv_action_1.setOnClickListener(this.onClickListener);
        this.tv_action_2.setOnClickListener(this.onClickListener);
        this.ll_phone.setOnClickListener(this.onClickListener);
        this.ll_remark.setOnClickListener(this.onClickListener);
    }

    public void remarkNameFail()
    {
        ToastUtils.showShortToast(2131690145);
    }

    public void remarkNameSuccess(String paramString)
    {
        ToastUtils.showShortToast(2131690146);
        this.tv_name.setText(paramString);
        this.friend.setRemarkName(paramString);
        this.tv_remark.setText(paramString);
    }

    protected void setPresenter()
    {
        this.presenter = new FriendInfoNewPresenter(this);
    }

    public void showLoading()
    {
        runOnUiThread(new FriendInfoNewActivity.7(this));
    }

    public void transferToChatActivity()
    {
        Intent localIntent = new Intent(this, MJTChatActivity.class);
        Bundle localBundle = new Bundle();
        localBundle.putSerializable("friend", this.friend);
        localIntent.putExtras(localBundle);
        startActivity(localIntent);
    }

    public void updateMode()
    {
        if (!this.friend.getUserId().equals("10006")) {
            if ((this.friend.getStatus() == 5) || (this.friend.getStatus() == 2) || (this.friend.getStatus() == -1)) {
                this.mode = 100;
            }
        }
        for (;;)
        {
            setMode(this.mode);
            return;
            this.mode = 101;
            continue;
            this.mode = 102;
        }
    }

    public void updateUi()
    {
        this.tv_name.setText(this.friend.getShowName());
    }
}

