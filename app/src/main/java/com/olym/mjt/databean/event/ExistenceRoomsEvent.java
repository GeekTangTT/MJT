package com.olym.mjt.databean.event;

import com.olym.mjt.utils.EventBusUtil;

public class ExistenceRoomsEvent
{
    private String friendid;

    public ExistenceRoomsEvent(String paramString)
    {
        this.friendid = paramString;
    }

    public static void post(ExistenceRoomsEvent paramExistenceRoomsEvent)
    {
        EventBusUtil.post(paramExistenceRoomsEvent);
    }

    public String getFriendid()
    {
        return this.friendid;
    }
}
