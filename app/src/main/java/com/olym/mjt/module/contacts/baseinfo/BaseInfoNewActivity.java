package com.olym.mjt.module.contacts.baseinfo;

import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.databean.bean.User;
import com.olym.mjt.glide.GlideUtil;
import com.olym.mjt.im.IMService;
import com.olym.mjt.utils.AvatarHelper;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.widget.dialog.LoadingDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427381)
public class BaseInfoNewActivity
        extends BaseTopbarActivity<BaseInfoPresenter>
        implements IBaseInfoView
{
    public static final String KEY_DATA = "data";
    private ServiceConnection IMServiceconn = new BaseInfoNewActivity.1(this);
    private String ibcDomain;
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
    private User user;
    private String userId;
    @ViewInject(2131231538)
    private View v_line_call;
    @ViewInject(2131231549)
    private View v_remark;

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

    public void activityfinish()
    {
        finish();
    }

    public void destroy()
    {
        unbindService(this.IMServiceconn);
    }

    public void getUser(User paramUser)
    {
        this.user = paramUser;
        this.tv_name.setText(paramUser.getNickName());
        this.tv_phone.setText(paramUser.getTelephone());
        this.tv_phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        this.ll_sip_call.setOnClickListener(new BaseInfoNewActivity.3(this, paramUser));
        this.tv_action_1.setOnClickListener(new BaseInfoNewActivity.4(this, paramUser));
        if (ChannelUtil.currentChannel == 102) {
            this.tv_action_1.setVisibility(8);
        }
    }

    public void handleBundle(Bundle paramBundle)
    {
        this.user = ((User)paramBundle.getSerializable("data"));
        if (this.user != null)
        {
            this.userId = this.user.getUserId();
            this.ibcDomain = this.user.getDomain();
            return;
        }
        this.userId = paramBundle.getString("userId");
        this.ibcDomain = paramBundle.getString("from_chat_ibcdomian");
        this.roomId = paramBundle.getString("from_chat_roomId");
        ((BaseInfoPresenter)this.presenter).getUserInfo(this.userId, this.ibcDomain, this.roomId);
    }

    public void init()
    {
        setTitleText(2131690118);
        bindService(new Intent(this, IMService.class), this.IMServiceconn, 1);
        GlideUtil.loadUserIcon(this, AvatarHelper.getAvatarUrl(this.userId, this.ibcDomain, false), this.iv_head);
        new Handler().postDelayed(new BaseInfoNewActivity.2(this), 1000L);
        this.tv_more.setVisibility(8);
        enableSip(true);
        enableXmpp(false);
        this.ll_sys_call.setVisibility(8);
        this.ll_sys_chat.setVisibility(8);
        this.tv_action_1.setText(2131689583);
        this.tv_action_2.setVisibility(8);
        this.ll_remark.setVisibility(8);
        this.v_remark.setVisibility(8);
        showCallRecorders(false);
        if (this.user != null) {
            getUser(this.user);
        }
    }

    protected void setPresenter()
    {
        this.presenter = new BaseInfoPresenter(this);
    }

    public void updateAdapter() {}
}
