package com.olym.mjt.module.contacts.phonecontacts;

import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.databean.event.LocalContactsChangeEvent;
import com.olym.mjt.databean.event.UpdateFriendInfoEvent;
import com.olym.mjt.im.IMService;
import com.olym.mjt.module.message.event.CardUIUpdateEvent;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.EventBusUtil;
import com.olym.mjt.utils.KeyboardHideUtil;
import com.olym.mjt.utils.ToastUtils;
import com.olym.mjt.widget.dialog.LoadingDialog;
import com.olym.mjt.widget.sortlist.SideBar;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427414)
public class PhoneContactsActivity
        extends BaseTopbarActivity<PhoneContactsPresenter>
        implements IPhoneContactsView
{
    public static final String KEY_FROM_ADD = "key_from_add";
    private static final int MODE_DECRY_DECODE = 2;
    private static final int MODE_NORMAL = 1;
    ServiceConnection IMServiceconn = new PhoneContactsActivity.5(this);
    private PhoneContactsAdapter adapter;
    private boolean isFromAdd = false;
    @ViewInject(2131231074)
    private PullToRefreshListView listview;
    private LoadingDialog loadingDialog;
    private IMService mImService;
    private View.OnClickListener onClickListener = new PhoneContactsActivity.6(this);
    @ViewInject(2131231362)
    private SideBar sidebar;
    @ViewInject(2131231529)
    private View tv_decode;
    @ViewInject(2131231531)
    private View tv_encry;
    @ViewInject(2131231532)
    private View tv_encry_decode;
    @ViewInject(2131231548)
    private View v_more;

    private void setMode(int paramInt)
    {
        if (paramInt == 1)
        {
            this.tv_encry_decode.setVisibility(0);
            this.tv_encry.setVisibility(8);
            this.tv_decode.setVisibility(8);
            this.adapter.setNormalMode(true);
        }
        while (paramInt != 2) {
            return;
        }
        this.tv_encry_decode.setVisibility(8);
        this.tv_encry.setVisibility(0);
        this.tv_decode.setVisibility(0);
        this.adapter.setNormalMode(false);
    }

    public void destroy()
    {
        unbindService(this.IMServiceconn);
        if ((this.loadingDialog != null) && (this.loadingDialog.isShowing())) {
            this.loadingDialog.dismiss();
        }
        EventBusUtil.unregister(this);
    }

    public void encryOrDecodeDone()
    {
        runOnUiThread(new PhoneContactsActivity.10(this));
    }

    public void handleBundle(Bundle paramBundle)
    {
        this.isFromAdd = paramBundle.getBoolean("key_from_add", false);
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleCardUIUpdateEvent(CardUIUpdateEvent paramCardUIUpdateEvent)
    {
        ((PhoneContactsPresenter)this.presenter).loadDataFromDB();
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleLocalContactsChange(LocalContactsChangeEvent paramLocalContactsChangeEvent)
    {
        ((PhoneContactsPresenter)this.presenter).loadDataFromDB();
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleUpdateFriendInfo(UpdateFriendInfoEvent paramUpdateFriendInfoEvent)
    {
        Applog.systemOut("----handleUpdateFriendInfo------" + paramUpdateFriendInfoEvent.getFriend());
        ((PhoneContactsPresenter)this.presenter).loadDataFromDB();
    }

    public void init()
    {
        setTitleText(getResources().getString(2131689684));
        setBackText(getResources().getString(2131690109));
        KeyboardHideUtil.init(this);
        ((PhoneContactsPresenter)this.presenter).setFromAdd(this.isFromAdd);
        this.loadingDialog = new LoadingDialog(this);
        EventBusUtil.register(this);
        this.tv_encry_decode.setOnClickListener(this.onClickListener);
        this.tv_encry.setOnClickListener(this.onClickListener);
        this.tv_decode.setOnClickListener(this.onClickListener);
        bindService(new Intent(this, IMService.class), this.IMServiceconn, 1);
        ((ListView)this.listview.getRefreshableView()).setDividerHeight(0);
        this.listview.setOnRefreshListener(new PhoneContactsActivity.1(this));
        this.listview.setOnScrollListener(new PhoneContactsActivity.2(this));
        this.sidebar.setOnTouchingLetterChangedListener(new PhoneContactsActivity.3(this));
        this.adapter = new PhoneContactsAdapter(this, ((PhoneContactsPresenter)this.presenter).getDatas(), (PhoneContactsPresenter)this.presenter);
        this.listview.setAdapter(this.adapter);
        this.v_more.setOnClickListener(new PhoneContactsActivity.4(this));
        if (this.isFromAdd) {
            this.v_more.setVisibility(8);
        }
    }

    public void networkError()
    {
        if (this.listview != null)
        {
            this.listview.onRefreshComplete();
            ToastUtils.showShortToastSafe(2131689941);
        }
    }

    protected void setPresenter()
    {
        this.presenter = new PhoneContactsPresenter(this);
    }

    public void updateAdapter()
    {
        if (this.adapter != null) {
            runOnUiThread(new PhoneContactsActivity.7(this));
        }
    }

    public void updateAdapterAndDatas()
    {
        if (this.adapter != null) {
            runOnUiThread(new PhoneContactsActivity.8(this));
        }
    }

    public void updateDatasError()
    {
        runOnUiThread(new PhoneContactsActivity.9(this));
    }
}
