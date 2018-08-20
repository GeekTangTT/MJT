package com.olym.mjt.module.contacts.addfriends;

import android.os.Bundle;
import android.view.View;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427355)
public class AddFriendActivity
        extends BaseTopbarActivity<AddFriendPresenter>
        implements IAddFriendView
{
    @ViewInject(2131231116)
    private View ll_phone_contacts;
    @ViewInject(2131231121)
    private View ll_search;

    public void destroy() {}

    public void handleBundle(Bundle paramBundle) {}

    public void init()
    {
        setTitleText(2131689583);
        this.ll_search.setOnClickListener(new AddFriendActivity.1(this));
        this.ll_phone_contacts.setOnClickListener(new AddFriendActivity.2(this));
    }

    protected void setPresenter()
    {
        this.presenter = new AddFriendPresenter(this);
    }
}

