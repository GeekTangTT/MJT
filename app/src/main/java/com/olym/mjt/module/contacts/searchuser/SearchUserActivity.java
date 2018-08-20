package com.olym.mjt.module.contacts.searchuser;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.utils.ToastUtils;
import com.olym.mjt.utils.ViewTransferUtil;
import com.olym.mjt.widget.ClearEditText;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427427)
public class SearchUserActivity
        extends BaseTopbarActivity<SearchUserPresenter>
        implements ISearchUserView
{
    @ViewInject(2131230931)
    private ClearEditText et_search;
    @ViewInject(2131231500)
    private TextView tv_search;

    private void search(String paramString)
    {
        if (paramString.equals(""))
        {
            ToastUtils.showShortToast(2131690179);
            return;
        }
        Bundle localBundle = new Bundle();
        localBundle.putString("data", paramString);
        ViewTransferUtil.transferToUserListActivity(this, localBundle);
    }

    public void destroy() {}

    public void handleBundle(Bundle paramBundle) {}

    public void init()
    {
        setTitleText(getResources().getString(2131689583));
        setBackText(getResources().getString(2131689620));
        this.et_search.setOnEditorActionListener(new SearchUserActivity.1(this));
        this.tv_search.setOnClickListener(new SearchUserActivity.2(this));
    }

    protected void setPresenter()
    {
        this.presenter = new SearchUserPresenter(this);
    }
}
