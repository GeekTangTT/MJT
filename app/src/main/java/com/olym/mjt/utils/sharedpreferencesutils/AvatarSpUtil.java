package com.olym.mjt.utils.sharedpreferencesutils;

public class AvatarSpUtil
{
    private static final String NAME = "avatar";
    private static volatile AvatarSpUtil instanse;
    private SPUtils spUtils = new SPUtils("avatar");

    public static AvatarSpUtil getInstanse()
    {
        if (instanse == null) {}
        try
        {
            if (instanse == null) {
                instanse = new AvatarSpUtil();
            }
            return instanse;
        }
        finally {}
    }

    private String getKey(String paramString1, String paramString2)
    {
        return paramString1 + "_" + paramString2;
    }

    public String getAvatar(String paramString1, String paramString2)
    {
        return this.spUtils.getString(getKey(paramString1, paramString2), null);
    }

    public void setAvatar(String paramString1, String paramString2, String paramString3)
    {
        this.spUtils.put(getKey(paramString1, paramString2), paramString3);
    }
}
