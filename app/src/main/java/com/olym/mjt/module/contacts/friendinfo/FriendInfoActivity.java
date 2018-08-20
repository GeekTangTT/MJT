package com.olym.mjt.module.contacts.friendinfo;

import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.database.dao.FriendDao;
import com.olym.mjt.database.dao.LocalContactDao;
import com.olym.mjt.databean.bean.Friend;
import com.olym.mjt.databean.bean.LocalContact;
import com.olym.mjt.databean.bean.User;
import com.olym.mjt.databean.event.UpdateFriendInfoEvent;
import com.olym.mjt.glide.GlideUtil;
import com.olym.mjt.im.IMService;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.module.message.event.MessageMsgUiUpdateEvent;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.AvatarHelper;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.utils.CharacterParser;
import com.olym.mjt.utils.ToastUtils;
import com.olym.mjt.widget.dialog.InputDialog;
import com.olym.mjt.widget.dialog.LoadingDialog;
import com.olym.mjt.widget.dialog.TipsDialog;
import com.olym.mjt.widget.dialog.TipsDialog.Build;
import com.suke.widget.SwitchButton;
import com.suke.widget.SwitchButton.OnCheckedChangeListener;
import de.hdodenhof.circleimageview.CircleImageView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427379)
public class FriendInfoActivity
        extends BaseTopbarActivity<FriendInfoPresenter>
        implements IFriendInfoView
{
    public static final String IS_SINGLE = "is_single";
    public static final String KEY_DATA = "data";
    public static final String ROOMID = "room_id";
    ServiceConnection ImSerViceConn = new FriendInfoActivity.5(this);
    @ViewInject(2131230751)
    private TextView app_chat;
    @ViewInject(2131230752)
    private TextView app_sip;
    private TipsDialog blackListTipsDialog;
    private TipsDialog deleteTipsDialog;
    private Friend friend;
    private InputDialog inputDialog;
    private boolean isBinded;
    private boolean isSingle = false;
    @ViewInject(2131231037)
    private CircleImageView iv_head;
    @ViewInject(2131231118)
    private View ll_recommend;
    @ViewInject(2131231119)
    private View ll_remark;
    @ViewInject(2131231122)
    private View ll_sip_call;
    @ViewInject(2131231133)
    private View ll_xmpp_chat;
    private LoadingDialog loadingDialog;
    private IMService mImService;
    private SwitchButton.OnCheckedChangeListener onCheckedChangeListener = new FriendInfoActivity.4(this);
    private View.OnClickListener onClickListener = new FriendInfoActivity.6(this);
    private String roomId;
    @ViewInject(2131231311)
    private SwitchButton sb_black;
    @ViewInject(2131231452)
    private TextView tv_delete;
    @ViewInject(2131231482)
    private TextView tv_name;
    @ViewInject(2131231488)
    private TextView tv_phone;
    @ViewInject(2131231496)
    private TextView tv_remark_name;

    public void deleteFriendSuccess()
    {
        finish();
    }

    public void destroy()
    {
        unbindService(this.ImSerViceConn);
        if (this.loadingDialog.isShowing()) {
            this.loadingDialog.dismiss();
        }
    }

    public void handleBundle(Bundle paramBundle)
    {
        this.friend = ((Friend)paramBundle.getSerializable("data"));
        this.roomId = paramBundle.getString("room_id");
        this.isSingle = paramBundle.getBoolean("is_single");
        Log.e("nail", "is single=" + this.isSingle);
        if (this.friend == null) {
            finish();
        }
    }

    public void hideLoading() {}

    public void init()
    {
        setTitleText(getResources().getString(2131690118));
        setBackText(getResources().getString(2131689620));
        if (ChannelUtil.currentChannel == 102)
        {
            this.app_chat.setText(getResources().getString(2131690108));
            this.app_sip.setText(getResources().getString(2131690112));
        }
        if (this.isSingle)
        {
            this.ll_xmpp_chat.setVisibility(4);
            this.ll_sip_call.setVisibility(4);
        }
        this.isBinded = bindService(new Intent(this, IMService.class), this.ImSerViceConn, 1);
        this.loadingDialog = new LoadingDialog(this);
        this.loadingDialog.setCancelable(true);
        this.blackListTipsDialog = new TipsDialog.Build(this).setCancelable(true).setCancelable(false).setContent(getResources().getString(2131689713)).setDialogClickListener(new FriendInfoActivity.1(this)).build();
        this.deleteTipsDialog = new TipsDialog.Build(this).setCancelable(true).setContent(getResources().getString(2131689721)).setDialogClickListener(new FriendInfoActivity.2(this)).build();
        ((FriendInfoPresenter)this.presenter).setFriend(this.friend, this.roomId);
        GlideUtil.loadUserIcon(this, AvatarHelper.getAvatarUrl(this.friend.getUserId(), this.friend.getDomain(), false), this.iv_head);
        new Handler().postDelayed(new FriendInfoActivity.3(this), 1000L);
        this.tv_name.setText(this.friend.getShowName());
        this.tv_remark_name.setText(this.friend.getRemarkName());
        this.tv_phone.setText(this.friend.getToTelephone());
        if (this.friend.getStatus() == -1) {
            this.sb_black.setChecked(true);
        }
        for (;;)
        {
            this.sb_black.setOnCheckedChangeListener(this.onCheckedChangeListener);
            this.ll_xmpp_chat.setOnClickListener(this.onClickListener);
            this.ll_sip_call.setOnClickListener(this.onClickListener);
            this.ll_remark.setOnClickListener(this.onClickListener);
            this.ll_recommend.setOnClickListener(this.onClickListener);
            this.tv_delete.setOnClickListener(this.onClickListener);
            if (this.friend.getStatus() == 5) {
                this.tv_delete.setVisibility(8);
            }
            return;
            this.sb_black.setChecked(false);
        }
    }

    protected void onNewIntent(Intent paramIntent)
    {
        super.onNewIntent(paramIntent);
        setIntent(paramIntent);
        this.isSingle = paramIntent.getExtras().getBoolean("is_single");
        if (this.isSingle)
        {
            this.ll_xmpp_chat.setVisibility(4);
            this.ll_sip_call.setVisibility(4);
        }
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
        this.tv_remark_name.setText(paramString);
        FriendDao.getInstance().setRemarkName(MjtApplication.getInstance().getLoginUser().getUserId(), this.friend.getUserId(), paramString, this.friend.getDomain());
        LocalContact localLocalContact = LocalContactDao.getInstance().getLocalContactsFromPhone(this.friend.getToTelephone());
        if (localLocalContact != null)
        {
            localLocalContact.setRemarkName(paramString);
            localLocalContact.setWholeSpell(new CharacterParser().getSellingWithPolyphone(paramString));
            localLocalContact.setSimplespell(new CharacterParser().getSimpleSellingPolyphone(paramString));
            LocalContactDao.getInstance().createOrUpdateContact(localLocalContact);
        }
        MessageMsgUiUpdateEvent.post(new MessageMsgUiUpdateEvent());
        Applog.systemOut("-----remarkNameSuccess----- " + this.friend);
        UpdateFriendInfoEvent.post(new UpdateFriendInfoEvent(this.friend));
    }

    protected void setPresenter()
    {
        this.presenter = new FriendInfoPresenter(this, this.mImService);
    }

    public void showLoading()
    {
        this.loadingDialog.show();
    }

    public void updateUi(Friend paramFriend)
    {
        this.tv_name.setText(paramFriend.getShowName());
    }
}
