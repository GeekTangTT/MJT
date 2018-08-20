package com.olym.mjt.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import com.zhy.autolayout.AutoRelativeLayout;

public class DialerCallBar
        extends AutoRelativeLayout
        implements View.OnClickListener, View.OnLongClickListener
{
    private OnDialActionListener actionListener;

    public DialerCallBar(Context paramContext)
    {
        this(paramContext, null, 0);
    }

    public DialerCallBar(Context paramContext, AttributeSet paramAttributeSet)
    {
        this(paramContext, paramAttributeSet, 0);
    }

    public DialerCallBar(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
        super(paramContext, paramAttributeSet);
        LayoutInflater.from(paramContext).inflate(2131427497, this, true);
        findViewById(2131230963).setOnClickListener(this);
        findViewById(2131230903).setOnClickListener(this);
        findViewById(2131230894).setOnClickListener(this);
        findViewById(2131230894).setOnLongClickListener(this);
    }

    public void onClick(View paramView)
    {
        int i;
        if (this.actionListener != null)
        {
            i = paramView.getId();
            if (i != 2131230903) {
                break label28;
            }
            this.actionListener.placeCall();
        }
        label28:
        do
        {
            return;
            if (i == 2131230894)
            {
                this.actionListener.deleteChar();
                return;
            }
        } while (i != 2131230963);
        this.actionListener.hide();
    }

    public boolean onLongClick(View paramView)
    {
        boolean bool2 = false;
        boolean bool1 = bool2;
        if (this.actionListener != null)
        {
            bool1 = bool2;
            if (paramView.getId() == 2131230894)
            {
                this.actionListener.deleteAll();
                paramView.setPressed(false);
                bool1 = true;
            }
        }
        return bool1;
    }

    public void setOnDialActionListener(OnDialActionListener paramOnDialActionListener)
    {
        this.actionListener = paramOnDialActionListener;
    }

    public static abstract interface OnDialActionListener
    {
        public abstract void deleteAll();

        public abstract void deleteChar();

        public abstract void hide();

        public abstract void placeCall();
    }
}
