package com.olym.mjt.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import com.zhy.autolayout.utils.AutoUtils;

public class TipsDialog
        extends Dialog
{
    public TipsDialog(Context paramContext)
    {
        super(paramContext);
    }

    public TipsDialog(Context paramContext, int paramInt)
    {
        super(paramContext, paramInt);
    }

    public TipsDialog(Context paramContext, boolean paramBoolean, DialogInterface.OnCancelListener paramOnCancelListener)
    {
        super(paramContext, paramBoolean, paramOnCancelListener);
    }

    public static class Build
    {
        private boolean cancelable = true;
        private String content;
        private Context context;
        private DialogClickListener dialogClickListener;
        private boolean isSingleChoise = false;
        private String title;

        public Build(Context paramContext)
        {
            this.context = paramContext;
        }

        public TipsDialog build()
        {
            final TipsDialog localTipsDialog = new TipsDialog(this.context, 2131755417);
            View localView = LayoutInflater.from(this.context).inflate(2131427557, null);
            AutoUtils.auto(localView);
            localTipsDialog.setContentView(localView, new ViewGroup.LayoutParams(-1, -1));
            if (this.cancelable)
            {
                localTipsDialog.setCancelable(true);
                localTipsDialog.setCanceledOnTouchOutside(true);
                if (this.dialogClickListener == null) {
                    break label209;
                }
                ((TextView)localView.findViewById(2131231456)).setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View paramAnonymousView)
                    {
                        TipsDialog.Build.this.dialogClickListener.onRightButtonClick(localTipsDialog);
                    }
                });
                label91:
                if (this.dialogClickListener == null) {
                    break label223;
                }
                ((TextView)localView.findViewById(2131231455)).setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View paramAnonymousView)
                    {
                        TipsDialog.Build.this.dialogClickListener.onLeftButtonClick(localTipsDialog);
                    }
                });
            }
            for (;;)
            {
                if (this.title != null) {
                    ((TextView)localView.findViewById(2131231457)).setText(this.title);
                }
                if (this.content != null) {
                    ((TextView)localView.findViewById(2131231454)).setText(this.content);
                }
                if (this.isSingleChoise)
                {
                    localView.findViewById(2131231533).setVisibility(8);
                    localView.findViewById(2131231455).setVisibility(8);
                }
                return localTipsDialog;
                localTipsDialog.setCancelable(false);
                localTipsDialog.setCanceledOnTouchOutside(false);
                break;
                label209:
                localView.findViewById(2131231456).setVisibility(8);
                break label91;
                label223:
                localView.findViewById(2131231455).setVisibility(8);
            }
        }

        public Build setCancelable(boolean paramBoolean)
        {
            this.cancelable = paramBoolean;
            return this;
        }

        public Build setContent(String paramString)
        {
            this.content = paramString;
            return this;
        }

        public Build setDialogClickListener(DialogClickListener paramDialogClickListener)
        {
            this.dialogClickListener = paramDialogClickListener;
            return this;
        }

        public Build setSingleChoise(boolean paramBoolean)
        {
            this.isSingleChoise = paramBoolean;
            return this;
        }

        public Build setTitle(String paramString)
        {
            this.title = paramString;
            return this;
        }
    }
}
