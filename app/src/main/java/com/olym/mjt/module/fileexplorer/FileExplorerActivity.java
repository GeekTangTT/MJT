package com.olym.mjt.module.fileexplorer;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.module.fileexplorer.utils.FileCategoryHelper;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427374)
public class FileExplorerActivity
        extends BaseTopbarActivity<FileExplorerPresenter>
        implements IFileExplorerView
{
    private FileCategoryHelper fileCategoryHelper;
    @ViewInject(2131231078)
    private View ll_all;
    @ViewInject(2131231093)
    private View ll_file;
    @ViewInject(2131231110)
    private View ll_music;
    @ViewInject(2131231117)
    private View ll_picture;
    @ViewInject(2131231131)
    private View ll_video;
    @ViewInject(2131231134)
    private View ll_zip;
    private View.OnClickListener onClickListener = new FileExplorerActivity.1(this);
    @ViewInject(2131231465)
    private TextView tv_file_num;
    @ViewInject(2131231481)
    private TextView tv_music_num;
    @ViewInject(2131231491)
    private TextView tv_picture_num;
    @ViewInject(2131231515)
    private TextView tv_video_num;
    @ViewInject(2131231517)
    private TextView tv_zip_num;

    public void destroy() {}

    public void handleBundle(Bundle paramBundle) {}

    public void init()
    {
        setTitleText(getResources().getString(2131689778));
        setBackText(getResources().getString(2131689915));
        this.ll_picture.setOnClickListener(this.onClickListener);
        this.ll_file.setOnClickListener(this.onClickListener);
        this.ll_music.setOnClickListener(this.onClickListener);
        this.ll_zip.setOnClickListener(this.onClickListener);
        this.ll_video.setOnClickListener(this.onClickListener);
        this.ll_all.setOnClickListener(this.onClickListener);
        this.fileCategoryHelper = new FileCategoryHelper(this);
        this.fileCategoryHelper.refreshCategoryInfo();
        this.tv_picture_num.setText(this.fileCategoryHelper.getCount(1) + "");
        this.tv_file_num.setText(this.fileCategoryHelper.getCount(2) + "");
        this.tv_music_num.setText(this.fileCategoryHelper.getCount(3) + "");
        this.tv_zip_num.setText(this.fileCategoryHelper.getCount(4) + "");
        this.tv_video_num.setText(this.fileCategoryHelper.getCount(5) + "");
    }

    protected void setPresenter()
    {
        this.presenter = new FileExplorerPresenter(this);
    }
}
