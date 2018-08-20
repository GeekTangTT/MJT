package com.olym.mjt.widget;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.module.gallery.PhotoUtils;

public class SelectPhotoPopupWindow
        extends PopupWindow
{
    private Context context;
    private View.OnClickListener onClickListener = new View.OnClickListener()
    {
        public void onClick(View paramAnonymousView)
        {
            switch (paramAnonymousView.getId())
            {
            }
            for (;;)
            {
                SelectPhotoPopupWindow.this.dismiss();
                return;
                PhotoUtils.openChoiseUserIcon((Activity)SelectPhotoPopupWindow.this.context);
                continue;
                MjtApplication.getInstance().setGotoSystemAppTime(System.currentTimeMillis());
                PhotoUtils.openCamareForUserIcon((Activity)SelectPhotoPopupWindow.this.context);
            }
        }
    };
    private TextView tv_cancel;
    private TextView tv_gallery;
    private TextView tv_photo;
    private View v_click;

    public SelectPhotoPopupWindow(Context paramContext)
    {
        this.context = paramContext;
        paramContext = LayoutInflater.from(paramContext).inflate(2131427568, null);
        this.tv_gallery = ((TextView)paramContext.findViewById(2131231472));
        this.tv_photo = ((TextView)paramContext.findViewById(2131231490));
        this.tv_cancel = ((TextView)paramContext.findViewById(2131231436));
        this.v_click = paramContext.findViewById(2131231528);
        this.tv_gallery.setOnClickListener(this.onClickListener);
        this.tv_photo.setOnClickListener(this.onClickListener);
        this.tv_cancel.setOnClickListener(this.onClickListener);
        this.v_click.setOnClickListener(this.onClickListener);
        setOutsideTouchable(true);
        setContentView(paramContext);
        setHeight(-1);
        setWidth(-1);
        setFocusable(true);
        setAnimationStyle(2131755418);
    }
}
