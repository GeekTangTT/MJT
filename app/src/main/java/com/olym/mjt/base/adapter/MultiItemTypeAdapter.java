package com.olym.mjt.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.List;

public class MultiItemTypeAdapter<T>
        extends BaseAdapter
{
    protected Context mContext;
    protected List<T> mDatas;
    private ItemViewDelegateManager mItemViewDelegateManager;

    public MultiItemTypeAdapter(Context paramContext, List<T> paramList)
    {
        this.mContext = paramContext;
        this.mDatas = paramList;
        this.mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    private boolean useItemViewDelegateManager()
    {
        return this.mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> paramItemViewDelegate)
    {
        this.mItemViewDelegateManager.addDelegate(paramItemViewDelegate);
        return this;
    }

    protected void convert(ViewHolder paramViewHolder, T paramT, int paramInt)
    {
        this.mItemViewDelegateManager.convert(paramViewHolder, paramT, paramInt);
    }

    public int getCount()
    {
        return this.mDatas.size();
    }

    public T getItem(int paramInt)
    {
        return (T)this.mDatas.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
        return paramInt;
    }

    public int getItemViewType(int paramInt)
    {
        if (useItemViewDelegateManager()) {
            return this.mItemViewDelegateManager.getItemViewType(this.mDatas.get(paramInt), paramInt);
        }
        return super.getItemViewType(paramInt);
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
        int i = this.mItemViewDelegateManager.getItemViewDelegate(this.mDatas.get(paramInt), paramInt).getItemViewLayoutId();
        if (paramView == null)
        {
            paramView = LayoutInflater.from(this.mContext).inflate(i, paramViewGroup, false);
            paramView = new ViewHolder(this.mContext, paramView, paramViewGroup, paramInt);
            paramView.mLayoutId = i;
            onViewHolderCreated(paramView, paramView.getConvertView());
        }
        for (;;)
        {
            convert(paramView, getItem(paramInt), paramInt);
            return paramView.getConvertView();
            paramView = (ViewHolder)paramView.getTag();
            paramView.mPosition = paramInt;
        }
    }

    public int getViewTypeCount()
    {
        if (useItemViewDelegateManager()) {
            return this.mItemViewDelegateManager.getItemViewDelegateCount();
        }
        return super.getViewTypeCount();
    }

    public void onViewHolderCreated(ViewHolder paramViewHolder, View paramView) {}
}
