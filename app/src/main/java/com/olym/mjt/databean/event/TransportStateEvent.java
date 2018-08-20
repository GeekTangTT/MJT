package com.olym.mjt.databean.event;

import com.olym.mjt.utils.EventBusUtil;

public class TransportStateEvent
{
    private int state;

    public TransportStateEvent(int paramInt)
    {
        this.state = paramInt;
    }

    public static void post(TransportStateEvent paramTransportStateEvent)
    {
        EventBusUtil.post(paramTransportStateEvent);
    }

    public int getState()
    {
        return this.state;
    }
}
