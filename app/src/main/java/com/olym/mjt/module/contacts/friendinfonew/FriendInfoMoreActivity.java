package com.olym.mjt.module.contacts.friendinfonew;

import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.databean.bean.Friend;
import com.olym.mjt.im.IMService;
import com.olym.mjt.module.message.event.MessageDeleteByFriendEvent;
import com.olym.mjt.utils.EventBusUtil;
import com.olym.mjt.utils.ToastUtils;
import com.olym.mjt.widget.dialog.TipsDialog;
import com.olym.mjt.widget.dialog.TipsDialog.Build;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427380)
public class FriendInfoMoreActivity
        extends BaseTopbarActivity<FriendInfoNewPresenter>
        implements IFriendInfoNewView
{
    public static final String KEY_DATA = "data";
    private ServiceConnection ImSerViceConn = new FriendInfoMoreActivity.2(this);
    private TipsDialog deleteTipsDialog;
    private Friend friend;
    @ViewInject(2131231087)
    private View ll_chat_files;
    @ViewInject(2131231088)
    private View ll_chat_history;
    @ViewInject(2131231118)
    private View ll_recommend;
    private IMService mImService;
    private View.OnClickListener onClickListener = new FriendInfoMoreActivity.3(this);
    @ViewInject(2131231452)
    private View tv_delete;
    @ViewInject(2131231458)
    private TextView tv_domain;

    private void initDeleteTips()
    {
        if (this.deleteTipsDialog == null) {
            this.deleteTipsDialog = new TipsDialog.Build(this).setCancelable(true).setContent(getResources().getString(2131689721)).setDialogClickListener(new FriendInfoMoreActivity.1(this)).build();
        }
    }

    public void deleteFriendSuccess()
    {
        finish();
    }

    public void destroy()
    {
        EventBusUtil.unregister(this);
        unbindService(this.ImSerViceConn);
    }

    public void handleBundle(Bundle paramBundle)
    {
        this.friend = ((Friend)paramBundle.getSerializable("data"));
        ((FriendInfoNewPresenter)this.presenter).setFriend(this.friend);
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
        setTitleText(2131689923);
        EventBusUtil.register(this);
        bindService(new Intent(this, IMService.class), this.ImSerViceConn, 1);
        String str2 = this.friend.getDomain();
        String str1 = str2;
        if (str2 != null)
        {
            str1 = str2;
            if (str2.contains(".")) {
                str1 = str2.split("[.]")[0];
            }
        }
        this.tv_domain.setText(str1);
        this.ll_recommend.setOnClickListener(this.onClickListener);
        this.ll_chat_files.setOnClickListener(this.onClickListener);
        this.ll_chat_history.setOnClickListener(this.onClickListener);
        this.tv_delete.setOnClickListener(this.onClickListener);
    }

    public void remarkNameFail() {}

    public void remarkNameSuccess(String paramString) {}

    protected void setPresenter()
    {
        this.presenter = new FriendInfoNewPresenter(this);
    }

    public void showLoading() {}

    public void transferToChatActivity() {}

    public void updateMode() {}

    public void updateUi() {}
}

