package com.olym.mjt.utils.sharedpreferencesutils;

public class TableVersionSp
{
    private static final String KEY_FRIEND_TABLE = "friend_";
    private static final String NAME = "table_version";
    private static volatile TableVersionSp instanse;
    private SPUtils spUtils = new SPUtils("table_version");

    public static TableVersionSp getInstanse()
    {
        if (instanse == null) {}
        try
        {
            if (instanse == null) {
                instanse = new TableVersionSp();
            }
            return instanse;
        }
        finally {}
    }

    public int getFriendTableVersion(String paramString)
    {
        return this.spUtils.getInt("friend_" + paramString, 0);
    }

    public void setFriendTableVersion(String paramString, int paramInt)
    {
        this.spUtils.put("friend_" + paramString, paramInt);
    }
}

