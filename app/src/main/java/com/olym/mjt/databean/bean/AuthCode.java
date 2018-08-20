package com.olym.mjt.databean.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class AuthCode
{
    @DatabaseField(generatedId=true)
    private int id;
    @DatabaseField(canBeNull=false)
    private int overdueTime;
    @DatabaseField(canBeNull=false)
    private String phoneNumber;
    @DatabaseField(canBeNull=false)
    private String randcode;

    public int getId()
    {
        return this.id;
    }

    public int getOverdueTime()
    {
        return this.overdueTime;
    }

    public String getPhoneNumber()
    {
        return this.phoneNumber;
    }

    public String getRandcode()
    {
        return this.randcode;
    }

    public void setId(int paramInt)
    {
        this.id = paramInt;
    }

    public void setOverdueTime(int paramInt)
    {
        this.overdueTime = paramInt;
    }

    public void setPhoneNumber(String paramString)
    {
        this.phoneNumber = paramString;
    }

    public void setRandcode(String paramString)
    {
        this.randcode = paramString;
    }
}

