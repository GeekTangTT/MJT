package com.olym.mjt.databean.bean;

public class VipInviteUser
{
    private String domain;
    private String nickname;
    private String tigase_id;
    private String user;
    private String version;

    public String getDomain()
    {
        return this.domain;
    }

    public String getNickname()
    {
        return this.nickname;
    }

    public String getTigase_id()
    {
        return this.tigase_id;
    }

    public String getUser()
    {
        return this.user;
    }

    public String getVersion()
    {
        return this.version;
    }

    public void setDomain(String paramString)
    {
        this.domain = paramString;
    }

    public void setNickname(String paramString)
    {
        this.nickname = paramString;
    }

    public void setTigase_id(String paramString)
    {
        this.tigase_id = paramString;
    }

    public void setUser(String paramString)
    {
        this.user = paramString;
    }

    public void setVersion(String paramString)
    {
        this.version = paramString;
    }
}
