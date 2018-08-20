package com.olym.mjt.base.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

public class ViewHolder
{
    private Context mContext;
    private View mConvertView;
    protected int mLayoutId;
    protected int mPosition;
    private SparseArray<View> mViews;

    public ViewHolder(Context paramContext, View paramView, ViewGroup paramViewGroup, int paramInt)
    {
        this.mContext = paramContext;
        this.mConvertView = paramView;
        this.mPosition = paramInt;
        this.mViews = new SparseArray();
        this.mConvertView.setTag(this);
    }

    public static ViewHolder get(Context paramContext, View paramView, ViewGroup paramViewGroup, int paramInt1, int paramInt2)
    {
        if (paramView == null)
        {
            paramContext = new ViewHolder(paramContext, LayoutInflater.from(paramContext).inflate(paramInt1, paramViewGroup, false), paramViewGroup, paramInt2);
            paramContext.mLayoutId = paramInt1;
            return paramContext;
        }
        paramContext = (ViewHolder)paramView.getTag();
        paramContext.mPosition = paramInt2;
        return paramContext;
    }

    public View getConvertView()
    {
        return this.mConvertView;
    }

    public int getItemPosition()
    {
        return this.mPosition;
    }

    public int getLayoutId()
    {
        return this.mLayoutId;
    }

    public <T extends View> T getView(int paramInt)
    {
        View localView2 = (View)this.mViews.get(paramInt);
        View localView1 = localView2;
        if (localView2 == null)
        {
            localView1 = this.mConvertView.findViewById(paramInt);
            this.mViews.put(paramInt, localView1);
        }
        return localView1;
    }

    public ViewHolder linkify(int paramInt)
    {
        Linkify.addLinks((TextView)getView(paramInt), 15);
        return this;
    }

    @SuppressLint({"NewApi"})
    public ViewHolder setAlpha(int paramInt, float paramFloat)
    {
        if (Build.VERSION.SDK_INT >= 11)
        {
            getView(paramInt).setAlpha(paramFloat);
            return this;
        }
        AlphaAnimation localAlphaAnimation = new AlphaAnimation(paramFloat, paramFloat);
        localAlphaAnimation.setDuration(0L);
        localAlphaAnimation.setFillAfter(true);
        getView(paramInt).startAnimation(localAlphaAnimation);
        return this;
    }

    public ViewHolder setBackgroundColor(int paramInt1, int paramInt2)
    {
        getView(paramInt1).setBackgroundColor(paramInt2);
        return this;
    }

    public ViewHolder setBackgroundRes(int paramInt1, int paramInt2)
    {
        getView(paramInt1).setBackgroundResource(paramInt2);
        return this;
    }

    public ViewHolder setChecked(int paramInt, boolean paramBoolean)
    {
        ((Checkable)getView(paramInt)).setChecked(paramBoolean);
        return this;
    }

    public ViewHolder setImageBitmap(int paramInt, Bitmap paramBitmap)
    {
        ((ImageView)getView(paramInt)).setImageBitmap(paramBitmap);
        return this;
    }

    public ViewHolder setImageDrawable(int paramInt, Drawable paramDrawable)
    {
        ((ImageView)getView(paramInt)).setImageDrawable(paramDrawable);
        return this;
    }

    public ViewHolder setImageResource(int paramInt1, int paramInt2)
    {
        ((ImageView)getView(paramInt1)).setImageResource(paramInt2);
        return this;
    }

    public ViewHolder setMax(int paramInt1, int paramInt2)
    {
        ((ProgressBar)getView(paramInt1)).setMax(paramInt2);
        return this;
    }

    public ViewHolder setOnClickListener(int paramInt, View.OnClickListener paramOnClickListener)
    {
        getView(paramInt).setOnClickListener(paramOnClickListener);
        return this;
    }

    public ViewHolder setOnLongClickListener(int paramInt, View.OnLongClickListener paramOnLongClickListener)
    {
        getView(paramInt).setOnLongClickListener(paramOnLongClickListener);
        return this;
    }

    public ViewHolder setOnTouchListener(int paramInt, View.OnTouchListener paramOnTouchListener)
    {
        getView(paramInt).setOnTouchListener(paramOnTouchListener);
        return this;
    }

    public ViewHolder setProgress(int paramInt1, int paramInt2)
    {
        ((ProgressBar)getView(paramInt1)).setProgress(paramInt2);
        return this;
    }

    public ViewHolder setProgress(int paramInt1, int paramInt2, int paramInt3)
    {
        ProgressBar localProgressBar = (ProgressBar)getView(paramInt1);
        localProgressBar.setMax(paramInt3);
        localProgressBar.setProgress(paramInt2);
        return this;
    }

    public ViewHolder setRating(int paramInt, float paramFloat)
    {
        ((RatingBar)getView(paramInt)).setRating(paramFloat);
        return this;
    }

    public ViewHolder setRating(int paramInt1, float paramFloat, int paramInt2)
    {
        RatingBar localRatingBar = (RatingBar)getView(paramInt1);
        localRatingBar.setMax(paramInt2);
        localRatingBar.setRating(paramFloat);
        return this;
    }

    public ViewHolder setTag(int paramInt1, int paramInt2, Object paramObject)
    {
        getView(paramInt1).setTag(paramInt2, paramObject);
        return this;
    }

    public ViewHolder setTag(int paramInt, Object paramObject)
    {
        getView(paramInt).setTag(paramObject);
        return this;
    }

    public ViewHolder setText(int paramInt, String paramString)
    {
        ((TextView)getView(paramInt)).setText(paramString);
        return this;
    }

    public ViewHolder setTextColor(int paramInt1, int paramInt2)
    {
        ((TextView)getView(paramInt1)).setTextColor(paramInt2);
        return this;
    }

    public ViewHolder setTextColorRes(int paramInt1, int paramInt2)
    {
        ((TextView)getView(paramInt1)).setTextColor(this.mContext.getResources().getColor(paramInt2));
        return this;
    }

    public ViewHolder setTypeface(Typeface paramTypeface, int... paramVarArgs)
    {
        int j = paramVarArgs.length;
        int i = 0;
        while (i < j)
        {
            TextView localTextView = (TextView)getView(paramVarArgs[i]);
            localTextView.setTypeface(paramTypeface);
            localTextView.setPaintFlags(localTextView.getPaintFlags() | 0x80);
            i += 1;
        }
        return this;
    }

    public ViewHolder setVisible(int paramInt, boolean paramBoolean)
    {
        View localView = getView(paramInt);
        if (paramBoolean) {}
        for (paramInt = 0;; paramInt = 8)
        {
            localView.setVisibility(paramInt);
            return this;
        }
    }

    public void updatePosition(int paramInt)
    {
        this.mPosition = paramInt;
    }
}
