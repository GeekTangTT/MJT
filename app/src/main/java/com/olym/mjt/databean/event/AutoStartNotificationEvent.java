package com.olym.mjt.databean.event;

import com.olym.mjt.utils.EventBusUtil;

public class AutoStartNotificationEvent
{
    public static void post(AutoStartNotificationEvent paramAutoStartNotificationEvent)
    {
        EventBusUtil.post(paramAutoStartNotificationEvent);
    }
}

