package com.olym.mjt.module.contacts.userlist;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.widget.dialog.LoadingDialog;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427439)
public class UserListActivity
        extends BaseTopbarActivity<UserListPresenter>
        implements IUserListView
{
    public static final String KEY_DATA = "data";
    private UserListAdapter adapter;
    @ViewInject(2131231074)
    private PullToRefreshListView listview;
    private LoadingDialog loadingDialog;
    private String searchKey;

    public void destroy()
    {
        if (this.loadingDialog.isShowing()) {
            this.loadingDialog.dismiss();
        }
    }

    public void handleBundle(Bundle paramBundle)
    {
        this.searchKey = paramBundle.getString("data", null);
        if (this.searchKey == null) {
            finish();
        }
    }

    public void hideLoading() {}

    public void init()
    {
        setTitleText(getResources().getString(2131690029));
        setBackText(getResources().getString(2131689583));
        this.loadingDialog = new LoadingDialog(this);
        this.loadingDialog.setCancelable(true);
        ((UserListPresenter)this.presenter).setKey(this.searchKey);
        View localView = LayoutInflater.from(this).inflate(2131427564, null);
        this.listview.setEmptyView(localView);
        ((ListView)this.listview.getRefreshableView()).setDividerHeight(0);
        this.adapter = new UserListAdapter(this, ((UserListPresenter)this.presenter).getDatas());
        this.listview.setAdapter(this.adapter);
    }

    protected void setPresenter()
    {
        this.presenter = new UserListPresenter(this);
    }

    public void showLoading() {}

    public void updateAdapter()
    {
        this.adapter.notifyDataSetChanged();
    }
}

