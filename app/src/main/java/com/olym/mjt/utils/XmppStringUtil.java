package com.olym.mjt.utils;

public class XmppStringUtil
{
    public static String getRoomJID(String paramString)
    {
        int i = paramString.indexOf("/");
        if (i <= 0) {
            return "";
        }
        return paramString.substring(0, i);
    }

    public static String getRoomJIDPrefix(String paramString)
    {
        int i = paramString.indexOf("@");
        if (i <= 0) {
            return "";
        }
        return paramString.substring(0, i);
    }

    public static String getRoomUserNick(String paramString)
    {
        int i = paramString.indexOf("/");
        if (i <= 0) {
            return "";
        }
        return paramString.substring(i + 1, paramString.length());
    }

    public static boolean isJID(String paramString)
    {
        return paramString.indexOf("@") > 0;
    }

    public static boolean isMucJID(String paramString1, String paramString2)
    {
        if ((paramString2 == null) || (paramString1 == null)) {}
        int i;
        do
        {
            return false;
            i = paramString2.indexOf(paramString1);
        } while ((i == 0) || (paramString2.charAt(i - 1) != '.'));
        return true;
    }
}

