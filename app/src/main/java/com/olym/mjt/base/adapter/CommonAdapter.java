package com.olym.mjt.base.adapter;

import android.content.Context;
import com.olym.mjt.base.presenter.BasePresenter;
import java.util.List;

public abstract class CommonAdapter<T, P extends BasePresenter>
        extends MultiItemTypeAdapter<T>
{
    protected P prsenter;

    public CommonAdapter(Context paramContext, final int paramInt, List<T> paramList, P paramP)
    {
        super(paramContext, paramList);
        this.prsenter = paramP;
        addItemViewDelegate(new ItemViewDelegate()
        {
            public void convert(ViewHolder paramAnonymousViewHolder, T paramAnonymousT, int paramAnonymousInt)
            {
                CommonAdapter.this.convert(paramAnonymousViewHolder, paramAnonymousT, paramAnonymousInt);
            }

            public int getItemViewLayoutId()
            {
                return paramInt;
            }

            public boolean isForViewType(T paramAnonymousT, int paramAnonymousInt)
            {
                return true;
            }
        });
    }

    protected abstract void convert(ViewHolder paramViewHolder, T paramT, int paramInt);
}

