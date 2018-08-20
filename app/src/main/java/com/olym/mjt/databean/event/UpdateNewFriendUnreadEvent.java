package com.olym.mjt.databean.event;

import com.olym.mjt.utils.EventBusUtil;

public class UpdateNewFriendUnreadEvent
{
    public static void post(UpdateNewFriendUnreadEvent paramUpdateNewFriendUnreadEvent)
    {
        EventBusUtil.post(paramUpdateNewFriendUnreadEvent);
    }
}
