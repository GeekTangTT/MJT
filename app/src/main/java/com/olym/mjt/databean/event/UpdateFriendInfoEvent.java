package com.olym.mjt.databean.event;

import com.olym.mjt.databean.bean.Friend;
import com.olym.mjt.utils.EventBusUtil;

public class UpdateFriendInfoEvent
{
    private Friend friend;

    public UpdateFriendInfoEvent(Friend paramFriend)
    {
        this.friend = paramFriend;
    }

    public static void post(UpdateFriendInfoEvent paramUpdateFriendInfoEvent)
    {
        EventBusUtil.postSticky(paramUpdateFriendInfoEvent);
    }

    public Friend getFriend()
    {
        return this.friend;
    }
}
