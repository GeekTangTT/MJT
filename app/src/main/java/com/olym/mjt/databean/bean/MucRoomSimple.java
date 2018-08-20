package com.olym.mjt.databean.bean;

public class MucRoomSimple
{
    private String desc;
    private String id;
    private String jid;
    private String name;
    private int timeSend;
    private String userId;

    public String getDesc()
    {
        return this.desc;
    }

    public String getId()
    {
        return this.id;
    }

    public String getJid()
    {
        return this.jid;
    }

    public String getName()
    {
        return this.name;
    }

    public int getTimeSend()
    {
        return this.timeSend;
    }

    public String getUserId()
    {
        return this.userId;
    }

    public void setDesc(String paramString)
    {
        this.desc = paramString;
    }

    public void setId(String paramString)
    {
        this.id = paramString;
    }

    public void setJid(String paramString)
    {
        this.jid = paramString;
    }

    public void setName(String paramString)
    {
        this.name = paramString;
    }

    public void setTimeSend(int paramInt)
    {
        this.timeSend = paramInt;
    }

    public void setUserId(String paramString)
    {
        this.userId = paramString;
    }
}
