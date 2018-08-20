package com.olym.mjt.module.contacts.baseinfo;

import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.widget.TextView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.database.dao.FriendDao;
import com.olym.mjt.databean.bean.Friend;
import com.olym.mjt.databean.bean.User;
import com.olym.mjt.glide.GlideUtil;
import com.olym.mjt.im.IMService;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.AvatarHelper;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;
import de.hdodenhof.circleimageview.CircleImageView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427360)
public class BaseInfoActivity
        extends BaseTopbarActivity<BaseInfoPresenter>
        implements IBaseInfoView
{
    public static final String KEY_DATA = "data";
    ServiceConnection IMServiceconn = new BaseInfoActivity.5(this);
    private String ibcDomain;
    private boolean isBinded;
    @ViewInject(2131231037)
    private CircleImageView iv_head;
    private IMService mImService;
    private String roomId;
    @ViewInject(2131231434)
    private TextView tv_add;
    @ViewInject(2131231482)
    private TextView tv_name;
    @ViewInject(2131231488)
    private TextView tv_phone;
    private User user;
    private String userId;

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
        upDateUI();
    }

    public void handleBundle(Bundle paramBundle)
    {
        this.userId = paramBundle.getString("userId");
        this.ibcDomain = paramBundle.getString("from_chat_ibcdomian");
        this.roomId = paramBundle.getString("from_chat_roomId");
        if (this.userId != null)
        {
            if (this.userId.equals(MjtApplication.getInstance().getLoginUser().getUserId()))
            {
                this.user = MjtApplication.getInstance().getLoginUser();
                return;
            }
            ((BaseInfoPresenter)this.presenter).getUserInfo(this.userId, this.ibcDomain, this.roomId);
            return;
        }
        this.user = ((User)paramBundle.getSerializable("data"));
        this.userId = this.user.getUserId();
    }

    public void init()
    {
        this.isBinded = bindService(new Intent(this, IMService.class), this.IMServiceconn, 1);
        setTitleText(getResources().getString(2131690114));
        setBackText(getResources().getString(2131689620));
        GlideUtil.loadUserIcon(this, AvatarHelper.getAvatarUrl(this.userId, this.ibcDomain, false), this.iv_head);
        new Handler().postDelayed(new BaseInfoActivity.1(this), 1000L);
        upDateUI();
    }

    protected void setPresenter()
    {
        this.presenter = new BaseInfoPresenter(this);
    }

    public void upDateUI()
    {
        if (this.user != null)
        {
            this.tv_name.setText(this.user.getNickName());
            this.tv_phone.setText(this.user.getTelephone());
            this.tv_phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
            localObject = UserSpUtil.getInstanse().getIBCDomain();
            if ((this.user.getUserId().equals(MjtApplication.getInstance().getLoginUser().getUserId())) && (((String)localObject).equals(this.ibcDomain))) {
                this.tv_add.setVisibility(8);
            }
        }
        else
        {
            return;
        }
        Object localObject = FriendDao.getInstance().getFriend(MjtApplication.getInstance().getLoginUser().getUserId(), this.userId, this.ibcDomain);
        Applog.systemOut("---friend--- " + localObject);
        if (localObject != null)
        {
            if (((Friend)localObject).getStatus() == 1)
            {
                this.tv_add.setVisibility(0);
                this.tv_add.setText(getResources().getString(2131689939));
                if (ChannelUtil.currentChannel == 102) {
                    this.tv_add.setVisibility(8);
                }
                this.tv_add.setOnClickListener(new BaseInfoActivity.2(this));
                return;
            }
            if (((Friend)localObject).getStatus() == 3)
            {
                this.tv_add.setVisibility(0);
                this.tv_add.setText(getResources().getString(2131689583));
                if (ChannelUtil.currentChannel == 102) {
                    this.tv_add.setVisibility(8);
                }
                this.tv_add.setOnClickListener(new BaseInfoActivity.3(this));
                return;
            }
            this.tv_add.setVisibility(8);
            return;
        }
        this.tv_add.setVisibility(0);
        if (ChannelUtil.currentChannel == 102) {
            this.tv_add.setVisibility(8);
        }
        this.tv_add.setOnClickListener(new BaseInfoActivity.4(this));
    }

    public void updateAdapter() {}
}

