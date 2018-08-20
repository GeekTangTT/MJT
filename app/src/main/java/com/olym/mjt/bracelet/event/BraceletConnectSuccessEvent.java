package com.olym.mjt.bracelet.event;

import com.olym.mjt.utils.EventBusUtil;

public class BraceletConnectSuccessEvent
{
    private boolean success = false;

    public BraceletConnectSuccessEvent(boolean paramBoolean)
    {
        this.success = paramBoolean;
    }

    public static void post(BraceletConnectSuccessEvent paramBraceletConnectSuccessEvent)
    {
        EventBusUtil.post(paramBraceletConnectSuccessEvent);
    }

    public boolean isSuccess()
    {
        return this.success;
    }
}

