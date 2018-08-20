package com.olym.mjt.module.mine;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.lc.methodex.LogFinalUtils;
import com.olym.mjt.base.fragment.BasePresenterFragment;
import com.olym.mjt.databean.bean.User;
import com.olym.mjt.databean.event.UpdateUserInfoEvent;
import com.olym.mjt.glide.GlideUtil;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.utils.AvatarHelper;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.utils.EventBusUtil;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;
import com.olym.mjt.widget.dialog.LoadingDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427507)
public class MineFragment
        extends BasePresenterFragment<MinePresenter>
        implements IMineView
{
    @ViewInject(2131230940)
    private TextView file_encrypt;
    @ViewInject(2131231037)
    private CircleImageView iv_head;
    @ViewInject(2131231096)
    private View ll_group2;
    @ViewInject(2131231101)
    private View ll_mine_contacts;
    @ViewInject(2131231102)
    private View ll_mine_file;
    @ViewInject(2131231103)
    private View ll_mine_info;
    @ViewInject(2131231104)
    private View ll_mine_keeplive;
    @ViewInject(2131231105)
    private View ll_mine_password;
    @ViewInject(2131231106)
    private View ll_mine_password_group;
    @ViewInject(2131231107)
    private View ll_mine_photo;
    @ViewInject(2131231108)
    private View ll_mine_settings;
    @ViewInject(2131231109)
    private View ll_mine_update;
    private LoadingDialog loadingDialog;
    private View.OnClickListener onClickListener = new MineFragment.1(this);
    @ViewInject(2131231244)
    private TextView photo_encrypt;
    @ViewInject(2131231482)
    private TextView tv_name;
    @ViewInject(2131231488)
    private TextView tv_phone;
    @ViewInject(2131231540)
    private View v_line_contacts;
    @ViewInject(2131231547)
    private View v_mine_keeplive;

    private int getVersionCode()
    {
        try
        {
            int i = this.activity.getPackageManager().getPackageInfo(this.activity.getPackageName(), 0).versionCode;
            return i;
        }
        catch (Exception localException)
        {
            LogFinalUtils.logForException(localException);
        }
        return -1;
    }

    public void destroy()
    {
        EventBusUtil.unregister(this);
        if ((this.loadingDialog != null) && (this.loadingDialog.isShowing())) {
            this.loadingDialog.dismiss();
        }
    }

    public void handleArguments() {}

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleUpdateUserInfo(UpdateUserInfoEvent paramUpdateUserInfoEvent)
    {
        updateUi();
    }

    public void init()
    {
        if (ChannelUtil.currentChannel == 102)
        {
            this.photo_encrypt.setText(getResources().getString(2131689978));
            this.file_encrypt.setText(getResources().getString(2131689780));
        }
        this.loadingDialog = new LoadingDialog(this.activity);
        this.ll_mine_info.setOnClickListener(this.onClickListener);
        this.ll_mine_photo.setOnClickListener(this.onClickListener);
        this.ll_mine_file.setOnClickListener(this.onClickListener);
        this.ll_mine_update.setOnClickListener(this.onClickListener);
        this.ll_mine_keeplive.setOnClickListener(this.onClickListener);
        this.ll_mine_settings.setOnClickListener(this.onClickListener);
        if (ChannelUtil.currentChannel == 102)
        {
            this.ll_mine_contacts.setVisibility(8);
            this.v_line_contacts.setVisibility(8);
        }
        this.ll_mine_contacts.setOnClickListener(this.onClickListener);
        if (ChannelUtil.action_gesture)
        {
            this.ll_mine_password_group.setVisibility(0);
            this.ll_mine_password.setOnClickListener(this.onClickListener);
        }
        for (;;)
        {
            if (this.iv_head != null)
            {
                User localUser = MjtApplication.getInstance().getLoginUser();
                String str = AvatarHelper.getAvatarUrl(localUser.getUserId(), UserSpUtil.getInstanse().getIBCDomain(), true);
                GlideUtil.loadUserIcon(MjtApplication.getInstance().getApplicationContext(), str, this.iv_head);
                str = localUser.getNickName();
                this.tv_name.setText(str);
                this.tv_phone.setText(localUser.getTelephone());
            }
            EventBusUtil.register(this);
            if (ChannelUtil.isUsedTEE) {
                this.ll_group2.setVisibility(8);
            }
            return;
            this.ll_mine_password_group.setVisibility(8);
        }
    }

    protected void setPresenter()
    {
        this.presenter = new MinePresenter(this);
    }

    public void updateUi()
    {
        if (this.iv_head != null)
        {
            User localUser = MjtApplication.getInstance().getLoginUser();
            String str = AvatarHelper.getAvatarUrl(localUser.getUserId(), UserSpUtil.getInstanse().getIBCDomain(), false);
            GlideUtil.loadUserIcon(MjtApplication.getInstance().getApplicationContext(), str, this.iv_head);
            str = localUser.getNickName();
            this.tv_name.setText(str);
            this.tv_phone.setText(localUser.getTelephone());
        }
    }
}
