package com.olym.mjt.databean.event;

import com.olym.mjt.utils.EventBusUtil;

public class UpdateUserInfoEvent
{
    public static void post(UpdateUserInfoEvent paramUpdateUserInfoEvent)
    {
        EventBusUtil.post(paramUpdateUserInfoEvent);
    }
}
