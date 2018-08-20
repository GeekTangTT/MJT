package com.olym.mjt.bracelet.event;

import com.olym.mjt.utils.EventBusUtil;

public class UnPairbraceletSuccessEvent
{
    public static void post(UnPairbraceletSuccessEvent paramUnPairbraceletSuccessEvent)
    {
        EventBusUtil.post(paramUnPairbraceletSuccessEvent);
    }
}
