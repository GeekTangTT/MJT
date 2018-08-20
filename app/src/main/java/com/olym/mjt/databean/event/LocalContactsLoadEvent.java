package com.olym.mjt.databean.event;

import com.olym.mjt.utils.EventBusUtil;

public class LocalContactsLoadEvent
{
    public static void post(LocalContactsLoadEvent paramLocalContactsLoadEvent)
    {
        EventBusUtil.postSticky(paramLocalContactsLoadEvent);
    }
}
