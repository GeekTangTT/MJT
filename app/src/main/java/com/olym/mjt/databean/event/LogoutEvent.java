package com.olym.mjt.databean.event;

import com.olym.mjt.utils.EventBusUtil;

public class LogoutEvent
{
    public static void post(LogoutEvent paramLogoutEvent)
    {
        EventBusUtil.post(paramLogoutEvent);
    }
}
