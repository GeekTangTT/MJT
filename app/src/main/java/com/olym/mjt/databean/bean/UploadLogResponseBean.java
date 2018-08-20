package com.olym.mjt.databean.bean;

public class UploadLogResponseBean
{
    private int code;
    private String message;

    public int getCode()
    {
        return this.code;
    }

    public String getError()
    {
        return this.message;
    }

    public void setCode(int paramInt)
    {
        this.code = paramInt;
    }

    public void setError(String paramString)
    {
        this.message = paramString;
    }

    public String toString()
    {
        return "UploadLogResponseBean{code=" + this.code + ", message='" + this.message + '\'' + '}';
    }
}

