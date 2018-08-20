package com.olym.mjt.databean.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class UserDomainBean
        implements Parcelable
{
    public static final Parcelable.Creator<UserDomainBean> CREATOR = new Parcelable.Creator()
    {
        public UserDomainBean createFromParcel(Parcel paramAnonymousParcel)
        {
            return new UserDomainBean(paramAnonymousParcel);
        }

        public UserDomainBean[] newArray(int paramAnonymousInt)
        {
            return new UserDomainBean[paramAnonymousInt];
        }
    };
    private String domain;
    private String domain_ip;
    private String tigase_id;
    private String user;
    private String version;

    public UserDomainBean() {}

    protected UserDomainBean(Parcel paramParcel)
    {
        this.user = paramParcel.readString();
        this.domain = paramParcel.readString();
        this.version = paramParcel.readString();
        this.tigase_id = paramParcel.readString();
        this.domain_ip = paramParcel.readString();
    }

    public int describeContents()
    {
        return 0;
    }

    public String getDomain()
    {
        return this.domain;
    }

    public String getDomain_ip()
    {
        return this.domain_ip;
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

    public void setDomain_ip(String paramString)
    {
        this.domain_ip = paramString;
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

    public String toString()
    {
        return "UserDomainBean{user='" + this.user + '\'' + ", domain='" + this.domain + '\'' + ", version='" + this.version + '\'' + ", tigase_id='" + this.tigase_id + '\'' + ", domain_ip='" + this.domain_ip + '\'' + '}';
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
        paramParcel.writeString(this.user);
        paramParcel.writeString(this.domain);
        paramParcel.writeString(this.version);
        paramParcel.writeString(this.tigase_id);
        paramParcel.writeString(this.domain_ip);
    }
}
