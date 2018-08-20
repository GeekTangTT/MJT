package com.olym.mjt.module.user.info;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.databean.bean.User;
import com.olym.mjt.databean.event.UpdateUserInfoEvent;
import com.olym.mjt.glide.GlideUtil;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.utils.AvatarHelper;
import com.olym.mjt.utils.ToastUtils;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;
import com.olym.mjt.widget.SelectPhotoPopupWindow;
import com.olym.mjt.widget.dialog.InputDialog;
import com.olym.mjt.widget.dialog.LoadingDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import java.io.File;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427387)
public class InfoActivity
        extends BaseTopbarActivity<InfoPresenter>
        implements IInfoView
{
    private String domain;
    private InputDialog inputDialog;
    @ViewInject(2131231037)
    private CircleImageView iv_head;
    @ViewInject(2131231097)
    private View ll_head;
    @ViewInject(2131231111)
    private View ll_name;
    private LoadingDialog loadingDialog;
    @ViewInject(2131231175)
    private TextView mine_domin;
    private View.OnClickListener onClickListener = new InfoActivity.1(this);
    private SelectPhotoPopupWindow selectPhotoPopupWindow;
    @ViewInject(2131231482)
    private TextView tv_name;
    @ViewInject(2131231488)
    private TextView tv_phone;
    private User user;

    public void destroy()
    {
        if ((this.loadingDialog != null) && (this.loadingDialog.isShowing())) {
            this.loadingDialog.dismiss();
        }
    }

    public void editNicknameSuccess()
    {
        this.user = MjtApplication.getInstance().getLoginUser();
        this.tv_name.setText(this.user.getNickName());
        hideLoading();
    }

    public void editUserHeadSuccess(String paramString)
    {
        ToastUtils.showShortToast(2131690237);
        GlideUtil.loadUserIcon(this, AvatarHelper.getAvatarUrl(MjtApplication.getInstance().getLoginUser().getUserId(), UserSpUtil.getInstanse().getIBCDomain(), true), this.iv_head);
        UpdateUserInfoEvent.post(new UpdateUserInfoEvent());
        hideLoading();
    }

    public void handleBundle(Bundle paramBundle) {}

    public void hideLoading()
    {
        this.loadingDialog.hide();
    }

    public void init()
    {
        setTitleText(getResources().getString(2131690122));
        setBackText(getResources().getString(2131689915));
        this.user = MjtApplication.getInstance().getLoginUser();
        this.domain = UserSpUtil.getInstanse().getUserDomain();
        if (!TextUtils.isEmpty(this.domain)) {
            this.mine_domin.setText(this.domain);
        }
        GlideUtil.loadUserIcon(this, AvatarHelper.getAvatarUrl(this.user.getUserId(), UserSpUtil.getInstanse().getIBCDomain(), false), this.iv_head);
        String str = this.user.getNickName();
        this.tv_name.setText(str);
        this.tv_phone.setText(this.user.getTelephone());
        this.ll_head.setOnClickListener(this.onClickListener);
        this.ll_name.setOnClickListener(this.onClickListener);
        this.loadingDialog = new LoadingDialog(this);
        this.selectPhotoPopupWindow = new SelectPhotoPopupWindow(this);
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
    {
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
        if ((paramInt1 == 1000) && (paramInt2 == -1) && (paramIntent != null))
        {
            paramIntent = paramIntent.getStringExtra("result");
            if (paramIntent != null)
            {
                File localFile = new File(paramIntent);
                if (localFile.exists())
                {
                    showLoading();
                    ((InfoPresenter)this.presenter).uploadUserHead(paramIntent, localFile);
                }
            }
        }
    }

    protected void setPresenter()
    {
        this.presenter = new InfoPresenter(this);
    }

    public void showLoading()
    {
        this.loadingDialog.show();
    }
}
