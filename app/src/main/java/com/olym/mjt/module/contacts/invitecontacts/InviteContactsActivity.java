package com.olym.mjt.module.contacts.invitecontacts;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.utils.KeyboardHideUtil;
import com.olym.mjt.widget.dialog.LoadingDialog;
import com.olym.mjt.widget.dialog.TipsDialog;
import com.olym.mjt.widget.dialog.TipsDialog.Build;
import com.olym.mjt.widget.sortlist.SideBar;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427388)
public class InviteContactsActivity
        extends BaseTopbarActivity<InviteContactsPresenter>
        implements IInviteContactsView
{
    private static final int MODE_EDIT = 2;
    private static final int MODE_NORMAL = 1;
    private InviteContactsAdapter adapter;
    private int currentMode = 1;
    @ViewInject(2131231029)
    private ImageView iv_choose_all;
    @ViewInject(2131231074)
    private PullToRefreshListView listview;
    @ViewInject(2131231079)
    private View ll_all_select;
    private LoadingDialog loadingDialog;
    @ViewInject(2131231279)
    private View rl_all_delete;
    @ViewInject(2131231362)
    private SideBar sideBar;
    private TipsDialog tipsDialog;
    @ViewInject(2131231431)
    private TextView tv_action;
    @ViewInject(2131231452)
    private View tv_delete;

    private void initTipsDialog()
    {
        if (this.tipsDialog == null) {
            this.tipsDialog = new TipsDialog.Build(this).setTitle(getResources().getString(2131689706)).setContent(getResources().getString(2131689707)).setCancelable(true).setDialogClickListener(new InviteContactsActivity.8(this)).build();
        }
    }

    private void setModeNormal(boolean paramBoolean)
    {
        if (paramBoolean)
        {
            this.tv_action.setText(2131689669);
            this.currentMode = 1;
            this.adapter.setNormalMode(true);
            this.rl_all_delete.setVisibility(8);
            return;
        }
        this.tv_action.setText(2131689644);
        this.currentMode = 2;
        this.adapter.setNormalMode(false);
        this.rl_all_delete.setVisibility(0);
    }

    public void destroy() {}

    public void handleBundle(Bundle paramBundle) {}

    public void init()
    {
        setTitleText(2131689917);
        KeyboardHideUtil.init(this);
        this.loadingDialog = new LoadingDialog(this);
        ((ListView)this.listview.getRefreshableView()).setDividerHeight(0);
        this.listview.setOnScrollListener(new InviteContactsActivity.1(this));
        this.sideBar.setOnTouchingLetterChangedListener(new InviteContactsActivity.2(this));
        this.adapter = new InviteContactsAdapter(this, ((InviteContactsPresenter)this.presenter).getDatas());
        this.listview.setAdapter(this.adapter);
        this.listview.setOnItemClickListener(new InviteContactsActivity.3(this));
        this.tv_action.setOnClickListener(new InviteContactsActivity.4(this));
        this.ll_all_select.setOnClickListener(new InviteContactsActivity.5(this));
        this.tv_delete.setOnClickListener(new InviteContactsActivity.6(this));
        this.tv_action.setVisibility(8);
    }

    protected void setPresenter()
    {
        this.presenter = new InviteContactsPresenter(this);
    }

    public void updateAdapterAndDatas()
    {
        if (this.adapter != null) {
            runOnUiThread(new InviteContactsActivity.7(this));
        }
    }
}

