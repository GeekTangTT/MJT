package com.olym.mjt.databean.event;

import com.olym.mjt.databean.bean.Friend;
import com.olym.mjt.utils.EventBusUtil;

public class EncryptContactsEvent
{
    private Friend friend;

    public EncryptContactsEvent(Friend paramFriend)
    {
        this.friend = paramFriend;
    }

    public static void post(EncryptContactsEvent paramEncryptContactsEvent)
    {
        EventBusUtil.post(paramEncryptContactsEvent);
    }

    public Friend getFriend()
    {
        return this.friend;
    }
}

