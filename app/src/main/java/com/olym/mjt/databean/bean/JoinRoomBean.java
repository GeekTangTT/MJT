package com.olym.mjt.databean.bean;

public class JoinRoomBean
{
    private int lastSecond;
    private String roomjid;

    public int getLastSecond()
    {
        return this.lastSecond;
    }

    public String getRoomjid()
    {
        return this.roomjid;
    }

    public void setLastSecond(int paramInt)
    {
        this.lastSecond = paramInt;
    }

    public void setRoomjid(String paramString)
    {
        this.roomjid = paramString;
    }
}

