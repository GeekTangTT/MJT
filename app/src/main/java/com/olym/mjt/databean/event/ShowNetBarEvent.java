package com.olym.mjt.databean.event;

import com.olym.mjt.utils.EventBusUtil;

public class ShowNetBarEvent
{
    private boolean isShow = false;

    public ShowNetBarEvent(boolean paramBoolean)
    {
        this.isShow = paramBoolean;
    }

    public static void post(ShowNetBarEvent paramShowNetBarEvent)
    {
        EventBusUtil.postSticky(paramShowNetBarEvent);
    }

    public boolean isShow()
    {
        return this.isShow;
    }
}
