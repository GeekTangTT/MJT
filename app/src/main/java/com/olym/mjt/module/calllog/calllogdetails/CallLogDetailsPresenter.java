package com.olym.mjt.module.calllog.calllogdetails;

import com.olym.mjt.base.presenter.BasePresenter;

public class CallLogDetailsPresenter
        extends BasePresenter
{
    private ICallLogDetailsView callLogDetailsView;

    public CallLogDetailsPresenter(ICallLogDetailsView paramICallLogDetailsView)
    {
        this.callLogDetailsView = paramICallLogDetailsView;
    }
}
