package com.olym.mjt.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.olym.mjt.utils.ChannelUtil;
import com.zhy.autolayout.AutoLayoutActivity;

public class ForbidScreenShotActivity
        extends AutoLayoutActivity
{
    protected void onCreate(@Nullable Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        if (ChannelUtil.currentChannel == 102) {}
    }
}

