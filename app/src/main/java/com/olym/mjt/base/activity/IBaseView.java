package com.olym.mjt.base.activity;

import android.os.Bundle;

public abstract interface IBaseView
{
    public abstract void destroy();

    public abstract void handleBundle(Bundle paramBundle);

    public abstract void init();

    public abstract void setAnim(int paramInt1, int paramInt2);
}
