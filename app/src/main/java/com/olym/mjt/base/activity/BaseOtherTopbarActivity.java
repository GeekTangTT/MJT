package com.olym.mjt.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.mjt.mjt.base.presenter.BasePresenter;
import org.xutils.view.annotation.ViewInject;
import qiu.niorgai.StatusBarCompat;

public abstract class BaseOtherTopbarActivity<P extends BasePresenter>
        extends BasePresenterActivity<P>
{
    @ViewInject(2131231129)
    private View ll_topbar_return;
    @ViewInject(2131231423)
    private ImageView topbar_return_iv;
    @ViewInject(2131231424)
    private TextView topbar_return_tv;
    @ViewInject(2131231425)
    private TextView topbar_title_tv;

    private void initTopbar()
    {
        this.ll_topbar_return.setOnClickListener(new BaseOtherTopbarActivity.1(this));
    }

    protected void backClick()
    {
        onBackPressed();
    }

    protected void onCreate(@Nullable Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        initTopbar();
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(2131034211));
    }

    protected void setBackButtonGone()
    {
        this.ll_topbar_return.setVisibility(8);
    }

    protected void setBackText(int paramInt)
    {
        this.topbar_return_tv.setText(getResources().getString(paramInt));
    }

    protected void setBackText(String paramString)
    {
        this.topbar_return_tv.setText(paramString);
    }

    protected void setTitleText(int paramInt)
    {
        this.topbar_title_tv.setText(getResources().getString(paramInt));
    }

    protected void setTitleText(String paramString)
    {
        this.topbar_title_tv.setText(paramString);
    }
}
