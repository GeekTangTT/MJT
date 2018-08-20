package com.olym.mjt.databean.event;

import com.olym.mjt.utils.EventBusUtil;

public class InCallViewDestroyEvent
{
    public static void post(InCallViewDestroyEvent paramInCallViewDestroyEvent)
    {
        EventBusUtil.post(paramInCallViewDestroyEvent);
    }
}
