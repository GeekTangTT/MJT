package com.olym.mjt.base.adapter;

import com.olym.mjt.base.presenter.BasePresenter;

public abstract class BaseItemViewDelegate<T, P extends BasePresenter>
        implements ItemViewDelegate<T>
{
    protected P presenter;

    public BaseItemViewDelegate(P paramP)
    {
        this.presenter = paramP;
    }
}
