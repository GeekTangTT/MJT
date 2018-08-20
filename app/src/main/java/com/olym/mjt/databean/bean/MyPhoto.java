package com.olym.mjt.databean.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class MyPhoto
{
    @DatabaseField
    private long createTime;
    @JSONField(name="oFileName")
    @DatabaseField
    private String originalFileName;
    @JSONField(name="oUrl")
    @DatabaseField
    private String originalUrl;
    @DatabaseField
    private String ownerId;
    @DatabaseField(id=true)
    private String photoId;
    @DatabaseField
    private int status;
    @JSONField(name="tUrl")
    @DatabaseField
    private String thumbUrl;

    public long getCreateTime()
    {
        return this.createTime;
    }

    public String getOriginalFileName()
    {
        return this.originalFileName;
    }

    public String getOriginalUrl()
    {
        return this.originalUrl;
    }

    public String getOwnerId()
    {
        return this.ownerId;
    }

    public String getPhotoId()
    {
        return this.photoId;
    }

    public int getStatus()
    {
        return this.status;
    }

    public String getThumbUrl()
    {
        return this.thumbUrl;
    }

    public void setCreateTime(long paramLong)
    {
        this.createTime = paramLong;
    }

    public void setOriginalFileName(String paramString)
    {
        this.originalFileName = paramString;
    }

    public void setOriginalUrl(String paramString)
    {
        this.originalUrl = paramString;
    }

    public void setOwnerId(String paramString)
    {
        this.ownerId = paramString;
    }

    public void setPhotoId(String paramString)
    {
        this.photoId = paramString;
    }

    public void setStatus(int paramInt)
    {
        this.status = paramInt;
    }

    public void setThumbUrl(String paramString)
    {
        this.thumbUrl = paramString;
    }
}
