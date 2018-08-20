package com.olym.mjt.base.adapter;

public abstract interface ItemViewDelegate<T>
{
    public abstract void convert(ViewHolder paramViewHolder, T paramT, int paramInt);

    public abstract int getItemViewLayoutId();

    public abstract boolean isForViewType(T paramT, int paramInt);
}
