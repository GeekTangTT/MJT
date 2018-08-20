package com.olym.mjt.databean.event;

import com.olym.mjt.utils.EventBusUtil;

public class NetworkChangeEvent
{
    private boolean isConnected;

    public NetworkChangeEvent(boolean paramBoolean)
    {
        this.isConnected = paramBoolean;
    }

    public static void post(NetworkChangeEvent paramNetworkChangeEvent)
    {
        try
        {
            EventBusUtil.post(paramNetworkChangeEvent);
            return;
        }
        finally
        {
            paramNetworkChangeEvent = finally;
            throw paramNetworkChangeEvent;
        }
    }

    public boolean isConnected()
    {
        return this.isConnected;
    }
}
