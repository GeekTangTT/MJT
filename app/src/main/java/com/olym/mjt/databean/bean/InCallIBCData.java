package com.olym.mjt.databean.bean;

public class InCallIBCData
{
    private String domain;
    private String phone_number;
    private String version;

    public String getDomain()
    {
        return this.domain;
    }

    public String getPhone_number()
    {
        return this.phone_number;
    }

    public String getVersion()
    {
        return this.version;
    }

    public void setDomain(String paramString)
    {
        this.domain = paramString;
    }

    public void setPhone_number(String paramString)
    {
        this.phone_number = paramString;
    }

    public void setVersion(String paramString)
    {
        this.version = paramString;
    }
}
