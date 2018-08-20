package com.olym.mjt.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.olym.mjt.base.presenter.BasePresenter;

public abstract class BasePresenterActivity<P extends BasePresenter>
        extends BaseActivity
{
    protected P presenter;

    protected void onCreate(@Nullable Bundle paramBundle)
    {
        setPresenter();
        super.onCreate(paramBundle);
        this.presenter.init();
    }

    public void onDestroy()
    {
        super.onDestroy();
        this.presenter.destroy();
    }

    protected abstract void setPresenter();
}

