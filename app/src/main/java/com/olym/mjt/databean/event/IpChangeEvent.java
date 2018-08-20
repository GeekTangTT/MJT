package com.olym.mjt.databean.event;

import com.olym.mjt.utils.EventBusUtil;

public class IpChangeEvent
{
    public static void post(IpChangeEvent paramIpChangeEvent)
    {
        EventBusUtil.post(paramIpChangeEvent);
    }
}
