package com.olym.mjt.module.calllog;

import com.olym.mjt.base.presenter.BasePresenter;

public class CallLogPresenter
        extends BasePresenter
{
    private ICallLogView callLogView;

    public CallLogPresenter(ICallLogView paramICallLogView)
    {
        this.callLogView = paramICallLogView;
    }
}
