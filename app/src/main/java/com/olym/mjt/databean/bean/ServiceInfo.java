package com.olym.mjt.databean.bean;

public class ServiceInfo
{
    private String android_version;
    private String api_version;
    private int bind_status;
    private int created_at;
    private int free_port = 11533;
    private int im_port = 8092;
    private String ios_version;
    private String p2p_ip;
    private String p2p_port;
    private int p2p_status = 0;
    private String permissions_whitelist;
    private int tigase_port = 5222;
    private String usercenter_ip;

    public String getAndroid_version()
    {
        return this.android_version;
    }

    public String getApi_version()
    {
        return this.api_version;
    }

    public int getBind_status()
    {
        return this.bind_status;
    }

    public int getCreated_at()
    {
        return this.created_at;
    }

    public int getFree_port()
    {
        return this.free_port;
    }

    public int getIm_port()
    {
        return this.im_port;
    }

    public String getIos_version()
    {
        return this.ios_version;
    }

    public String getP2p_ip()
    {
        return this.p2p_ip;
    }

    public String getP2p_port()
    {
        return this.p2p_port;
    }

    public int getP2p_status()
    {
        return this.p2p_status;
    }

    public String getPermissions_whitelist()
    {
        return this.permissions_whitelist;
    }

    public int getTigase_port()
    {
        return this.tigase_port;
    }

    public String getUsercenter_ip()
    {
        return this.usercenter_ip;
    }

    public void setAndroid_version(String paramString)
    {
        this.android_version = paramString;
    }

    public void setApi_version(String paramString)
    {
        this.api_version = paramString;
    }

    public void setBind_status(int paramInt)
    {
        this.bind_status = paramInt;
    }

    public void setCreated_at(int paramInt)
    {
        this.created_at = paramInt;
    }

    public void setFree_port(int paramInt)
    {
        this.free_port = paramInt;
    }

    public void setIm_port(int paramInt)
    {
        this.im_port = paramInt;
    }

    public void setIos_version(String paramString)
    {
        this.ios_version = paramString;
    }

    public void setP2p_ip(String paramString)
    {
        this.p2p_ip = paramString;
    }

    public void setP2p_port(String paramString)
    {
        this.p2p_port = paramString;
    }

    public void setP2p_status(int paramInt)
    {
        this.p2p_status = paramInt;
    }

    public void setPermissions_whitelist(String paramString)
    {
        this.permissions_whitelist = paramString;
    }

    public void setTigase_port(int paramInt)
    {
        this.tigase_port = paramInt;
    }

    public void setUsercenter_ip(String paramString)
    {
        this.usercenter_ip = paramString;
    }

    public String toString()
    {
        return "ServiceInfo{p2p_status=" + this.p2p_status + ", bind_status=" + this.bind_status + ", p2p_ip='" + this.p2p_ip + '\'' + ", p2p_port='" + this.p2p_port + '\'' + ", ios_version='" + this.ios_version + '\'' + ", android_version='" + this.android_version + '\'' + ", usercenter_ip='" + this.usercenter_ip + '\'' + ", tigase_port=" + this.tigase_port + ", free_port=" + this.free_port + ", permissions_whitelist='" + this.permissions_whitelist + '\'' + ", created_at=" + this.created_at + ", api_version='" + this.api_version + '\'' + ", im_port=" + this.im_port + '}';
    }
}

