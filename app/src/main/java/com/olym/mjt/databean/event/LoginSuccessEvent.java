package com.olym.mjt.databean.event;

import com.olym.mjt.utils.EventBusUtil;

public class LoginSuccessEvent
{
    public static void post(LoginSuccessEvent paramLoginSuccessEvent)
    {
        EventBusUtil.post(paramLoginSuccessEvent);
    }
}
