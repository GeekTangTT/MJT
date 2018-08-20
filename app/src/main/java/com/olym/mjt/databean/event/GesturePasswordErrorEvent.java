package com.olym.mjt.databean.event;

import com.olym.mjt.utils.EventBusUtil;

public class GesturePasswordErrorEvent
{
    public static final int TYPE_GOTO_LOGIN_VIEW = 2;
    public static final int TYPE_LOGOUT = 1;
    private int type;

    public GesturePasswordErrorEvent(int paramInt)
    {
        this.type = paramInt;
    }

    public static void post(GesturePasswordErrorEvent paramGesturePasswordErrorEvent)
    {
        EventBusUtil.post(paramGesturePasswordErrorEvent);
    }

    public int getType()
    {
        return this.type;
    }
}
