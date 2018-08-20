package com.olym.mjt.utils;

import xiaofei.library.hermeseventbus.HermesEventBus;

public class EventBusUtil
{
    public static void post(Object paramObject)
    {
        HermesEventBus.getDefault().post(paramObject);
    }

    public static void postSticky(Object paramObject)
    {
        HermesEventBus.getDefault().postSticky(paramObject);
    }

    public static void register(Object paramObject)
    {
        HermesEventBus.getDefault().register(paramObject);
    }

    public static void removeAllStickyEvents()
    {
        HermesEventBus.getDefault().removeAllStickyEvents();
    }

    public static void unregister(Object paramObject)
    {
        HermesEventBus.getDefault().unregister(paramObject);
    }
}

