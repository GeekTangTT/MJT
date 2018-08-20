package com.olym.mjt.databean.event;

import com.olym.mjt.utils.EventBusUtil;

public class NewFriendNumEvent
{
    public static void post(NewFriendNumEvent paramNewFriendNumEvent)
    {
        EventBusUtil.post(paramNewFriendNumEvent);
    }
}
