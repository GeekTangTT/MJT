package com.olym.mjt.databean.event;

import com.olym.mjt.utils.EventBusUtil;

public class SetPjsipLogEvent
{
    private int logLevel;

    public SetPjsipLogEvent(int paramInt)
    {
        this.logLevel = paramInt;
    }

    public static void post(SetPjsipLogEvent paramSetPjsipLogEvent)
    {
        EventBusUtil.post(paramSetPjsipLogEvent);
    }

    public int getLogLevel()
    {
        return this.logLevel;
    }
}
