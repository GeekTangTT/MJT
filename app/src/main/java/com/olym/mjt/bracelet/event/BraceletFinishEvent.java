package com.olym.mjt.bracelet.event;

import com.olym.mjt.utils.EventBusUtil;

public class BraceletFinishEvent
{
    public static void post(BraceletFinishEvent paramBraceletFinishEvent)
    {
        EventBusUtil.post(paramBraceletFinishEvent);
    }
}
