package com.olym.mjt.databean.event;

import com.olym.mjt.utils.EventBusUtil;

public class CallMissedNumEvent
{
    public static void post(CallMissedNumEvent paramCallMissedNumEvent)
    {
        EventBusUtil.post(paramCallMissedNumEvent);
    }
}
