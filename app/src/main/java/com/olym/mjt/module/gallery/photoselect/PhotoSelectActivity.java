package com.olym.mjt.module.gallery.photoselect;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.TextView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.module.gallery.model.PhotoFolderInfo;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.widget.dialog.LoadingDialog;
import java.util.ArrayList;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427417)
public class PhotoSelectActivity
        extends BaseTopbarActivity<PhotoSelectPresenter>
        implements IPhotoSelectView
{
    public static final String KEY_DATAS = "datas";
    private PhotoSelectAdapter adapter;
    @ViewInject(2131230960)
    private GridView gridview;
    private LoadingDialog loadingDialog;
    private View.OnClickListener onClickListener = new PhotoSelectActivity.3(this);
    private PhotoFolderInfo photoFolderInfo;
    @ViewInject(2131231450)
    private TextView tv_decode;
    @ViewInject(2131231461)
    private TextView tv_encry;
    @ViewInject(2131231504)
    private TextView tv_selected_count;
    private int type;

    public void destroy()
    {
        if ((this.loadingDialog != null) && (this.loadingDialog.isShowing())) {
            this.loadingDialog.dismiss();
        }
    }

    public void encryDecryDone()
    {
        runOnUiThread(new PhotoSelectActivity.2(this));
    }

    public void handleBundle(Bundle paramBundle)
    {
        this.type = paramBundle.getInt("type", 1);
        this.photoFolderInfo = ((PhotoFolderInfo)paramBundle.getSerializable("datas"));
    }

    public void init()
    {
        setTitleText(this.photoFolderInfo.getFolderName());
        setBackText(getResources().getString(2131689811));
        this.loadingDialog = new LoadingDialog(this);
        if (ChannelUtil.currentChannel == 102)
        {
            this.tv_encry.setText(getResources().getString(2131689766));
            this.tv_decode.setText(getResources().getString(2131689700));
        }
        if (this.type == 1)
        {
            this.tv_encry.setVisibility(8);
            this.tv_decode.setVisibility(8);
            this.tv_selected_count.setVisibility(8);
        }
        for (;;)
        {
            ((PhotoSelectPresenter)this.presenter).setDatas((ArrayList)this.photoFolderInfo.getPhotoList());
            this.adapter = new PhotoSelectAdapter(this, (PhotoSelectPresenter)this.presenter, (ArrayList)this.photoFolderInfo.getPhotoList(), this.type);
            this.gridview.setAdapter(this.adapter);
            this.gridview.setOnScrollListener(new PhotoSelectActivity.1(this));
            return;
            if (this.type == 2)
            {
                this.tv_encry.setVisibility(8);
                this.tv_decode.setVisibility(8);
                this.tv_selected_count.setVisibility(0);
            }
            else if (this.type == 3)
            {
                this.tv_encry.setVisibility(0);
                this.tv_decode.setVisibility(0);
                this.tv_selected_count.setVisibility(8);
                this.tv_decode.setOnClickListener(this.onClickListener);
                this.tv_encry.setOnClickListener(this.onClickListener);
            }
        }
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
    {
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
        if (paramInt1 == 1000)
        {
            setResult(-1, paramIntent);
            finish();
        }
    }

    protected void setPresenter()
    {
        this.presenter = new PhotoSelectPresenter(this);
    }

    public void updateCurrentSelected(int paramInt)
    {
        this.tv_selected_count.setText(getResources().getString(2131689672) + "(" + paramInt + "/9)");
    }
}

