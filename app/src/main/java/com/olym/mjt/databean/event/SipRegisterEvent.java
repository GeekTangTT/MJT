package com.olym.mjt.databean.event;

import com.olym.mjt.utils.EventBusUtil;

public class SipRegisterEvent
{
    private int code;

    public SipRegisterEvent(int paramInt)
    {
        this.code = paramInt;
    }

    public static void post(SipRegisterEvent paramSipRegisterEvent)
    {
        EventBusUtil.post(paramSipRegisterEvent);
    }

    public int getCode()
    {
        return this.code;
    }
}
