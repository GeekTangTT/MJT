package com.olym.mjt.base.adapter;

import android.support.v4.util.SparseArrayCompat;

public class ItemViewDelegateManager<T>
{
    SparseArrayCompat<ItemViewDelegate<T>> delegates = new SparseArrayCompat();

    public ItemViewDelegateManager<T> addDelegate(int paramInt, ItemViewDelegate<T> paramItemViewDelegate)
    {
        if (this.delegates.get(paramInt) != null) {
            throw new IllegalArgumentException("An ItemViewDelegate is already registered for the viewType = " + paramInt + ". Already registered ItemViewDelegate is " + this.delegates.get(paramInt));
        }
        this.delegates.put(paramInt, paramItemViewDelegate);
        return this;
    }

    public ItemViewDelegateManager<T> addDelegate(ItemViewDelegate<T> paramItemViewDelegate)
    {
        int i = this.delegates.size();
        if (paramItemViewDelegate != null) {
            this.delegates.put(i, paramItemViewDelegate);
        }
        return this;
    }

    public void convert(ViewHolder paramViewHolder, T paramT, int paramInt)
    {
        int j = this.delegates.size();
        int i = 0;
        while (i < j)
        {
            ItemViewDelegate localItemViewDelegate = (ItemViewDelegate)this.delegates.valueAt(i);
            if (localItemViewDelegate.isForViewType(paramT, paramInt))
            {
                localItemViewDelegate.convert(paramViewHolder, paramT, paramInt);
                return;
            }
            i += 1;
        }
        throw new IllegalArgumentException("No ItemViewDelegateManager added that matches position=" + paramInt + " in data source");
    }

    public ItemViewDelegate getItemViewDelegate(T paramT, int paramInt)
    {
        int i = this.delegates.size() - 1;
        while (i >= 0)
        {
            ItemViewDelegate localItemViewDelegate = (ItemViewDelegate)this.delegates.valueAt(i);
            if (localItemViewDelegate.isForViewType(paramT, paramInt)) {
                return localItemViewDelegate;
            }
            i -= 1;
        }
        throw new IllegalArgumentException("No ItemViewDelegate added that matches position=" + paramInt + " in data source");
    }

    public int getItemViewDelegateCount()
    {
        return this.delegates.size();
    }

    public int getItemViewLayoutId(int paramInt)
    {
        return ((ItemViewDelegate)this.delegates.get(paramInt)).getItemViewLayoutId();
    }

    public int getItemViewLayoutId(T paramT, int paramInt)
    {
        return getItemViewDelegate(paramT, paramInt).getItemViewLayoutId();
    }

    public int getItemViewType(ItemViewDelegate paramItemViewDelegate)
    {
        return this.delegates.indexOfValue(paramItemViewDelegate);
    }

    public int getItemViewType(T paramT, int paramInt)
    {
        int i = this.delegates.size() - 1;
        while (i >= 0)
        {
            if (((ItemViewDelegate)this.delegates.valueAt(i)).isForViewType(paramT, paramInt)) {
                return this.delegates.keyAt(i);
            }
            i -= 1;
        }
        throw new IllegalArgumentException("No ItemViewDelegate added that matches position=" + paramInt + " in data source");
    }

    public ItemViewDelegateManager<T> removeDelegate(int paramInt)
    {
        paramInt = this.delegates.indexOfKey(paramInt);
        if (paramInt >= 0) {
            this.delegates.removeAt(paramInt);
        }
        return this;
    }

    public ItemViewDelegateManager<T> removeDelegate(ItemViewDelegate<T> paramItemViewDelegate)
    {
        if (paramItemViewDelegate == null) {
            throw new NullPointerException("ItemViewDelegate is null");
        }
        int i = this.delegates.indexOfValue(paramItemViewDelegate);
        if (i >= 0) {
            this.delegates.removeAt(i);
        }
        return this;
    }
}
