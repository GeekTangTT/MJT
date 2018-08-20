package com.olym.mjt.bracelet.event;

import com.olym.mjt.utils.EventBusUtil;

public class BraceleteAgainEvent
{
    public static void post(BraceleteAgainEvent paramBraceleteAgainEvent)
    {
        EventBusUtil.post(paramBraceleteAgainEvent);
    }
}
