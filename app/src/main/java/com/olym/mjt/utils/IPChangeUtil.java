package com.olym.mjt.utils;

import com.olym.mjt.config.AppConfig;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;
import java.util.ArrayList;

public class IPChangeUtil
{
    private static int index = 0;
    private static ArrayList<String> ips = new ArrayList();

    static
    {
        ips.add("202.181.150.5:443");
        ips.add("202.74.3.141:443");
    }

    public static void change()
    {
        String str2 = (String)ips.get(index);
        String str1 = str2.substring(0, str2.lastIndexOf(":"));
        str2 = str2.substring(str2.lastIndexOf(":"), str2.length());
        UserSpUtil.getInstanse().setUserIP(str1);
        UserSpUtil.getInstanse().setUserDomian(ChannelUtil.domain);
        FileSpUtils.getInstanse().setUserDomian(ChannelUtil.domain);
        UserSpUtil.getInstanse().setUserUrl(str1);
        UserSpUtil.getInstanse().setUserPort(str2);
        UserSpUtil.getInstanse().setIpCheckDate(System.currentTimeMillis());
        AppConfig.init();
        int i = index + 1;
        index = i;
        index = i % ips.size();
    }
}

