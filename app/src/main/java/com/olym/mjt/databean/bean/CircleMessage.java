package com.olym.mjt.databean.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class CircleMessage
{
    @DatabaseField(generatedId=true)
    private int id;
    @DatabaseField(canBeNull=false)
    private String msgId;
    @DatabaseField(canBeNull=false)
    private String ownerId;
    @DatabaseField
    private long time;
    @DatabaseField(canBeNull=false)
    private String userId;

    public String getMsgId()
    {
        return this.msgId;
    }

    public String getOwnerId()
    {
        return this.ownerId;
    }

    public long getTime()
    {
        return this.time;
    }

    public String getUserId()
    {
        return this.userId;
    }

    public void setMsgId(String paramString)
    {
        this.msgId = paramString;
    }

    public void setOwnerId(String paramString)
    {
        this.ownerId = paramString;
    }

    public void setTime(long paramLong)
    {
        this.time = paramLong;
    }

    public void setUserId(String paramString)
    {
        this.userId = paramString;
    }
}

