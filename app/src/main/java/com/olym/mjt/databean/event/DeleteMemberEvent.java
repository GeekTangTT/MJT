package com.olym.mjt.databean.event;

import com.olym.mjt.utils.EventBusUtil;

public class DeleteMemberEvent
{
    private String toUserId;

    public DeleteMemberEvent(String paramString)
    {
        this.toUserId = paramString;
    }

    public static void post(DeleteMemberEvent paramDeleteMemberEvent)
    {
        EventBusUtil.post(paramDeleteMemberEvent);
    }

    public String getToUserId()
    {
        return this.toUserId;
    }
}
