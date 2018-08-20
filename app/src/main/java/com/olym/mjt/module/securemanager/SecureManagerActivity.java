package com.olym.mjt.module.securemanager;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427428)
public class SecureManagerActivity
        extends BaseTopbarActivity<SecureManagerPresenter>
        implements ISecureManagerView
{
    @ViewInject(2131231085)
    private View ll_change_pass;
    @ViewInject(2131231094)
    private View ll_gesture_password;
    private View.OnClickListener onClickListener = new SecureManagerActivity.1(this);

    public void destroy() {}

    public void handleBundle(Bundle paramBundle) {}

    public void init()
    {
        setTitleText(2131690034);
        this.ll_change_pass.setOnClickListener(this.onClickListener);
        this.ll_gesture_password.setOnClickListener(this.onClickListener);
    }

    protected void setPresenter()
    {
        this.presenter = new SecureManagerPresenter(this);
    }
}
