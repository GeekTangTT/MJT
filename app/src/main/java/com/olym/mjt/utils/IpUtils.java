package com.olym.mjt.utils;

import com.olym.mjt.module.MjtApplication;

public class IpUtils
{
    public static boolean isWifi()
    {
        return String.valueOf(NetworkUtil.getNetworkType(MjtApplication.getInstance().getApplicationContext())).equals("Wifi");
    }
}

