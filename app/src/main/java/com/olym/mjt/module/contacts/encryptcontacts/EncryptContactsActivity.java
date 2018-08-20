package com.olym.mjt.module.contacts.encryptcontacts;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.databean.event.EncryptContactsEvent;
import com.olym.mjt.utils.EventBusUtil;
import com.olym.mjt.widget.dialog.LoadingDialog;
import com.olym.mjt.widget.sortlist.SideBar;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427371)
public class EncryptContactsActivity
        extends BaseTopbarActivity<EncryptContactsPresenter>
        implements IEncryptContactsView
{
    private static final int MODE_EDIT = 2;
    private static final int MODE_NORMAL = 1;
    private EncryptContactsAdapter adapter;
    private int currentMode = 1;
    @ViewInject(2131231029)
    private ImageView iv_choose_all;
    @ViewInject(2131231074)
    private PullToRefreshListView listview;
    @ViewInject(2131231079)
    private View ll_all_select;
    private LoadingDialog loadingDialog;
    @ViewInject(2131231278)
    private View rl_all_action;
    @ViewInject(2131231362)
    private SideBar sidebar;
    @ViewInject(2131231451)
    private View tv_decrypt;
    @ViewInject(2131231460)
    private TextView tv_edit;
    @ViewInject(2131231462)
    private View tv_encrypt;

    private void setModeNormal(boolean paramBoolean)
    {
        if (paramBoolean)
        {
            this.tv_edit.setText(2131689669);
            this.currentMode = 1;
            this.adapter.setNormalMode(true);
            this.rl_all_action.setVisibility(8);
            return;
        }
        this.tv_edit.setText(2131689644);
        this.currentMode = 2;
        this.adapter.setNormalMode(false);
        this.rl_all_action.setVisibility(0);
        this.iv_choose_all.setSelected(false);
    }

    public void destroy()
    {
        if ((this.loadingDialog != null) && (this.loadingDialog.isShowing())) {
            this.loadingDialog.dismiss();
        }
        EventBusUtil.unregister(this);
    }

    public void encryOrDecodeDone()
    {
        runOnUiThread(new EncryptContactsActivity.9(this));
    }

    public void handleBundle(Bundle paramBundle) {}

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleEncryptContacts(EncryptContactsEvent paramEncryptContactsEvent)
    {
        paramEncryptContactsEvent = paramEncryptContactsEvent.getFriend();
        this.adapter.updateFriends(paramEncryptContactsEvent);
    }

    public void init()
    {
        setTitleText(2131689976);
        EventBusUtil.register(this);
        this.loadingDialog = new LoadingDialog(this);
        ((ListView)this.listview.getRefreshableView()).setDividerHeight(0);
        this.listview.setReflashable(false);
        this.listview.setOnScrollListener(new EncryptContactsActivity.1(this));
        this.sidebar.setOnTouchingLetterChangedListener(new EncryptContactsActivity.2(this));
        this.adapter = new EncryptContactsAdapter(this, ((EncryptContactsPresenter)this.presenter).getDatas(), (EncryptContactsPresenter)this.presenter);
        this.listview.setAdapter(this.adapter);
        this.tv_edit.setOnClickListener(new EncryptContactsActivity.3(this));
        this.ll_all_select.setOnClickListener(new EncryptContactsActivity.4(this));
        this.tv_decrypt.setOnClickListener(new EncryptContactsActivity.5(this));
        this.tv_encrypt.setOnClickListener(new EncryptContactsActivity.6(this));
    }

    protected void setPresenter()
    {
        this.presenter = new EncryptContactsPresenter(this);
    }

    public void showLoading()
    {
        runOnUiThread(new EncryptContactsActivity.10(this));
    }

    public void updateAdapter()
    {
        if (this.adapter != null) {
            runOnUiThread(new EncryptContactsActivity.7(this));
        }
    }

    public void updateAdapterAndDatas()
    {
        if (this.adapter != null) {
            runOnUiThread(new EncryptContactsActivity.8(this));
        }
    }
}
