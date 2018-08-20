package com.olym.mjt.module.gallery.photofolder;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ListView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.databean.event.UpdatePhotoFolderInfoEvent;
import com.olym.mjt.utils.EventBusUtil;
import com.olym.mjt.widget.dialog.LoadingDialog;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427415)
public class PhotoFolderActivity
        extends BaseTopbarActivity<PhotoFolderPresenter>
        implements IPhotoFolderView
{
    private PhotoFolderAdapter adapter;
    private Bundle bundle;
    private boolean isNeedLoadDatas = true;
    @ViewInject(2131231074)
    private ListView listView;
    private LoadingDialog loadingDialog;

    public void destroy()
    {
        EventBusUtil.unregister(this);
        if (this.loadingDialog.isShowing()) {
            this.loadingDialog.dismiss();
        }
    }

    public void handleBundle(Bundle paramBundle)
    {
        this.bundle = paramBundle;
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleUpdatePhotoFolderInfo(UpdatePhotoFolderInfoEvent paramUpdatePhotoFolderInfoEvent)
    {
        this.isNeedLoadDatas = true;
    }

    public void hideLoading()
    {
        if (this.loadingDialog != null) {
            this.loadingDialog.hide();
        }
    }

    public void init()
    {
        setTitleText(getResources().getString(2131689811));
        setBackText(getResources().getString(2131689915));
        this.loadingDialog = new LoadingDialog(this);
        EventBusUtil.register(this);
        this.adapter = new PhotoFolderAdapter(this, ((PhotoFolderPresenter)this.presenter).getDatas(), this.bundle);
        this.listView.setAdapter(this.adapter);
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
    {
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
        if ((paramInt1 == 1000) && (paramInt2 == -1))
        {
            setResult(-1, paramIntent);
            finish();
        }
    }

    protected void onResume()
    {
        super.onResume();
        if (this.isNeedLoadDatas)
        {
            ((PhotoFolderPresenter)this.presenter).updateDatas();
            this.isNeedLoadDatas = false;
        }
    }

    protected void setPresenter()
    {
        this.presenter = new PhotoFolderPresenter(this);
    }

    public void showLoading()
    {
        if (this.loadingDialog != null) {
            this.loadingDialog.show();
        }
    }

    public void updateUi()
    {
        this.adapter.notifyDataSetChanged();
        hideLoading();
    }
}
