package com.olym.mjt.databean.bean;

public class UploadEncryptFileResult
{
    private String code;
    private String error;
    private String respond_md5_value;
    private int status;
    private String url;

    public String getCode()
    {
        return this.code;
    }

    public String getError()
    {
        return this.error;
    }

    public int getStatus()
    {
        return this.status;
    }

    public String getUrl()
    {
        return this.url;
    }

    public void setCode(String paramString)
    {
        this.code = paramString;
    }

    public void setError(String paramString)
    {
        this.error = paramString;
    }

    public void setStatus(int paramInt)
    {
        this.status = paramInt;
    }

    public void setUrl(String paramString)
    {
        this.url = paramString;
    }
}
