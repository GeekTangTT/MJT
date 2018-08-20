package com.olym.mjt.databean.event;

import com.olym.mjt.utils.EventBusUtil;

public class LocalContactsChangeEvent
{
    private boolean isDelete = false;

    public LocalContactsChangeEvent(boolean paramBoolean)
    {
        this.isDelete = paramBoolean;
    }

    public static void post(LocalContactsChangeEvent paramLocalContactsChangeEvent)
    {
        EventBusUtil.post(paramLocalContactsChangeEvent);
    }

    public boolean isDelete()
    {
        return this.isDelete;
    }
}
