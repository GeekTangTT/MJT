package com.olym.mjt.databean.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class VideoFile
{
    @DatabaseField(generatedId=true)
    private int _id;
    @DatabaseField(canBeNull=false)
    private String createTime;
    @DatabaseField
    private String desc;
    @DatabaseField
    private long fileLength;
    @DatabaseField(canBeNull=false)
    private String filePath;
    @DatabaseField
    private long fileSize;
    @DatabaseField(canBeNull=false)
    private String ownerId;

    public String getCreateTime()
    {
        return this.createTime;
    }

    public String getDesc()
    {
        return this.desc;
    }

    public long getFileLength()
    {
        return this.fileLength;
    }

    public String getFilePath()
    {
        return this.filePath;
    }

    public long getFileSize()
    {
        return this.fileSize;
    }

    public String getOwnerId()
    {
        return this.ownerId;
    }

    public int get_id()
    {
        return this._id;
    }

    public void setCreateTime(String paramString)
    {
        this.createTime = paramString;
    }

    public void setDesc(String paramString)
    {
        this.desc = paramString;
    }

    public void setFileLength(long paramLong)
    {
        this.fileLength = paramLong;
    }

    public void setFilePath(String paramString)
    {
        this.filePath = paramString;
    }

    public void setFileSize(long paramLong)
    {
        this.fileSize = paramLong;
    }

    public void setOwnerId(String paramString)
    {
        this.ownerId = paramString;
    }

    public void set_id(int paramInt)
    {
        this._id = paramInt;
    }
}

