package com.olym.mjt.databean.event;

import com.olym.mjt.utils.EventBusUtil;

public class DataReadyEvent
{
    public static void post(DataReadyEvent paramDataReadyEvent)
    {
        EventBusUtil.post(paramDataReadyEvent);
    }
}
