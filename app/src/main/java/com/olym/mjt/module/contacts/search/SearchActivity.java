package com.olym.mjt.module.contacts.search;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.olym.mjt.base.activity.BasePresenterActivity;
import com.olym.mjt.databean.bean.Friend;
import com.olym.mjt.databean.bean.User;
import com.olym.mjt.databean.event.EncryptContactsEvent;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.network.client.HttpsClient;
import com.olym.mjt.utils.KeyboardHideUtil;
import com.olym.mjt.utils.StatusBarUtil;
import com.olym.mjt.utils.ToastUtils;
import com.olym.mjt.widget.dialog.LoadingDialog;
import java.util.Timer;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427423)
public class SearchActivity
        extends BasePresenterActivity<SearchPresenter>
        implements ISearchView
{
    public static final int ACTION_ADD_FRIEND = 100;
    public static final int ACTION_ENCRYPT_CONTACTS = 105;
    public static final int ACTION_SEARCH_CONTACTS = 102;
    public static final int ACTION_SEARCH_FRIEND = 101;
    public static final int ACTION_SEARCH_GROUP = 103;
    public static final int ACTION_SEARCH_INVITE = 104;
    public static final String KEY_ACTION = "key_action";
    public static final String KEY_DATA = "key_data";
    private int action;
    private Bundle bundle;
    @ViewInject(2131230925)
    private EditText et_content;
    @ViewInject(2131231032)
    private ImageView iv_delete;
    @ViewInject(2131231074)
    private PullToRefreshListView listview;
    @ViewInject(2131231076)
    private View ll_add_friend_search;
    private LoadingDialog loadingDialog;
    private SearchAdapter searchAdapter;
    private SearchGroupAdapter searchGroupAdapter;
    @ViewInject(2131231436)
    private TextView tv_cancel;
    @ViewInject(2131231501)
    private TextView tv_search1;
    @ViewInject(2131231502)
    private TextView tv_search2;
    @ViewInject(2131231510)
    private TextView tv_title;

    private void setKey(String paramString)
    {
        if (paramString.equals(MjtApplication.getInstance().getLoginUser().getTelephone()))
        {
            ToastUtils.showLongToast(2131690140);
            return;
        }
        this.loadingDialog.show();
        HttpsClient.getInstanse().searchUsers(paramString, new SearchActivity.7(this));
    }

    public void destroy()
    {
        if (this.loadingDialog.isShowing()) {
            this.loadingDialog.hide();
        }
    }

    public void encryOrDecodeDone(Friend paramFriend)
    {
        this.searchAdapter.notifyDataSetChanged();
        EncryptContactsEvent.post(new EncryptContactsEvent(paramFriend));
    }

    public void handleBundle(Bundle paramBundle)
    {
        this.action = paramBundle.getInt("key_action", -1);
        this.bundle = paramBundle;
    }

    public void init()
    {
        StatusBarUtil.setStatusBar(this);
        KeyboardHideUtil.init(this);
        ((SearchPresenter)this.presenter).setAction(this.action, this.bundle);
        this.loadingDialog = new LoadingDialog(this);
        if (this.action == 100) {
            this.et_content.setInputType(3);
        }
        String str = getResources().getString(2131690029);
        this.tv_search1.setText(str + ":");
        this.et_content.addTextChangedListener(new SearchActivity.1(this));
        this.iv_delete.setOnClickListener(new SearchActivity.2(this));
        this.tv_cancel.setOnClickListener(new SearchActivity.3(this));
        ((ListView)this.listview.getRefreshableView()).setDividerHeight(0);
        this.listview.setOnScrollListener(new SearchActivity.4(this));
        if (this.action == 103)
        {
            this.searchGroupAdapter = new SearchGroupAdapter(this, ((SearchPresenter)this.presenter).getGroups());
            this.listview.setAdapter(this.searchGroupAdapter);
        }
        for (;;)
        {
            this.ll_add_friend_search.setOnClickListener(new SearchActivity.5(this));
            new Timer().schedule(new SearchActivity.6(this), 500L);
            return;
            this.searchAdapter = new SearchAdapter(this, ((SearchPresenter)this.presenter).getDatas(), this.action, (SearchPresenter)this.presenter);
            this.listview.setAdapter(this.searchAdapter);
        }
    }

    public void onBackPressed()
    {
        KeyboardHideUtil.hideKeyboard(this, this.et_content.getWindowToken());
        super.onBackPressed();
    }

    protected void setPresenter()
    {
        this.presenter = new SearchPresenter(this);
    }

    public void updateAdapter()
    {
        if (this.searchAdapter != null) {
            runOnUiThread(new SearchActivity.8(this));
        }
        while (this.searchGroupAdapter == null) {
            return;
        }
        runOnUiThread(new SearchActivity.9(this));
    }
}

