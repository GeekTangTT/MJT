package com.olym.mjt.databean.event;

import com.olym.mjt.utils.EventBusUtil;

public class ExitsMuchRoomEvents
{
    private String toUserId;

    public ExitsMuchRoomEvents(String paramString)
    {
        this.toUserId = paramString;
    }

    public static void post(ExitsMuchRoomEvents paramExitsMuchRoomEvents)
    {
        EventBusUtil.post(paramExitsMuchRoomEvents);
    }

    public String getToUserId()
    {
        return this.toUserId;
    }
}
