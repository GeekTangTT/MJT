package com.olym.mjt.utils.sharedpreferencesutils;

import android.content.Context;

public class XmppSpUtil
{
    public static final String KEY_AUTOLOGIN_TIME = "autologin_time";
    public static final String KEY_OFFLINE_TIME = "offline_time";
    public static final String KEY_SERVER_TIME = "server_time";
    public static final String NAME = "xmpp";
    public static final String REQUEST_TIMES = "request_times";
    private static volatile XmppSpUtil instanse;
    private SPUtils spUtils = new SPUtils("xmpp");

    public static XmppSpUtil getInstanse()
    {
        if (instanse == null) {}
        try
        {
            if (instanse == null) {
                instanse = new XmppSpUtil();
            }
            return instanse;
        }
        finally {}
    }

    public long getAutoLoginTime()
    {
        return this.spUtils.getLong("autologin_time");
    }

    public boolean getBoolean(Context paramContext, String paramString)
    {
        return this.spUtils.getBoolean(paramString, false);
    }

    public Long getLong(Context paramContext, String paramString)
    {
        return getLong(paramContext, paramString, 1L);
    }

    public Long getLong(Context paramContext, String paramString, long paramLong)
    {
        return Long.valueOf(this.spUtils.getLong(paramString, paramLong));
    }

    public long getOfflineTime()
    {
        return this.spUtils.getLong("offline_time");
    }

    public int getRequestTimes(String paramString)
    {
        return this.spUtils.getInt(paramString);
    }

    public long getServerTime()
    {
        return this.spUtils.getLong("server_time");
    }

    public void putBoolean(Context paramContext, String paramString, boolean paramBoolean)
    {
        this.spUtils.put(paramString, paramBoolean);
    }

    public void putLong(Context paramContext, String paramString, long paramLong)
    {
        long l = paramLong;
        if (paramString.equals("offline_time"))
        {
            paramLong -= 30L;
            l = paramLong;
            if (paramLong <= 0L) {
                l = 0L;
            }
        }
        this.spUtils.put(paramString, l);
    }

    public void setKeyAutologinTime(long paramLong)
    {
        this.spUtils.put("autologin_time", paramLong);
    }

    public void setOfflineTime(long paramLong)
    {
        this.spUtils.put("offline_time", paramLong);
    }

    public void setRequestTimes(String paramString, int paramInt)
    {
        this.spUtils.put(paramString, paramInt);
    }

    public void setServerTime(long paramLong)
    {
        this.spUtils.put("server_time", paramLong);
    }
}

