package com.olym.mjt.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.olym.mjt.base.presenter.BasePresenter;

public abstract class BasePresenterFragment<P extends BasePresenter>
        extends BaseFragment
{
    protected P presenter;

    public BasePresenterFragment()
    {
        setPresenter();
    }

    public void onDestroyView()
    {
        super.onDestroyView();
        this.presenter.destroy();
    }

    public void onViewCreated(View paramView, @Nullable Bundle paramBundle)
    {
        super.onViewCreated(paramView, paramBundle);
        this.presenter.init();
    }

    protected abstract void setPresenter();
}
