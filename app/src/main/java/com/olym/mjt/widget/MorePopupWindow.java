package com.olym.mjt.widget;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import com.olym.mjt.utils.ViewTransferUtil;
import com.zhy.autolayout.utils.AutoUtils;
import com.zhy.autolayout.utils.ScreenUtils;

public class MorePopupWindow
{
    private Activity context;
    private PopupWindow popupWindow;

    public MorePopupWindow(Activity paramActivity)
    {
        this.context = paramActivity;
        initPopup();
    }

    private void initPopup()
    {
        View localView2 = LayoutInflater.from(this.context).inflate(2131427569, null, false);
        this.popupWindow = new PopupWindow(localView2, AutoUtils.getPercentWidthSize(276), AutoUtils.getPercentHeightSize(190), true);
        View localView1 = localView2.findViewById(2131231095);
        localView2 = localView2.findViewById(2131231121);
        localView1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                MorePopupWindow.this.popupWindow.dismiss();
                ViewTransferUtil.transferToSelectContectsActivity(MorePopupWindow.this.context);
            }
        });
        localView2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                MorePopupWindow.this.popupWindow.dismiss();
                ViewTransferUtil.transferToSearchUserActivity(MorePopupWindow.this.context);
            }
        });
        this.popupWindow.setBackgroundDrawable(new BitmapDrawable());
        this.popupWindow.setFocusable(true);
        this.popupWindow.setOutsideTouchable(true);
        this.popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener()
        {
            public void onDismiss()
            {
                WindowManager.LayoutParams localLayoutParams = MorePopupWindow.this.context.getWindow().getAttributes();
                localLayoutParams.alpha = 1.0F;
                MorePopupWindow.this.context.getWindow().clearFlags(2);
                MorePopupWindow.this.context.getWindow().setAttributes(localLayoutParams);
            }
        });
    }

    public void show(View paramView)
    {
        int[] arrayOfInt = new int[2];
        paramView.getLocationOnScreen(arrayOfInt);
        int i = arrayOfInt[0];
        i = arrayOfInt[1];
        arrayOfInt = ScreenUtils.getScreenSize(this.context, false);
        this.popupWindow.showAtLocation(paramView, 0, arrayOfInt[0] - AutoUtils.getPercentWidthSize(292), paramView.getHeight() + i + AutoUtils.getPercentHeightSize(26));
    }
}
