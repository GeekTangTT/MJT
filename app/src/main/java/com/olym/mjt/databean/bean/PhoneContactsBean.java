package com.olym.mjt.databean.bean;

import java.util.ArrayList;

public class PhoneContactsBean
{
    private ArrayList<LocalContact> localContactList;
    private String postData;

    public PhoneContactsBean(String paramString, ArrayList<LocalContact> paramArrayList)
    {
        this.postData = paramString;
        this.localContactList = paramArrayList;
    }

    public ArrayList<LocalContact> getLocalContactList()
    {
        return this.localContactList;
    }

    public String getPostData()
    {
        return this.postData;
    }

    public void setLocalContactList(ArrayList<LocalContact> paramArrayList)
    {
        this.localContactList = paramArrayList;
    }

    public void setPostData(String paramString)
    {
        this.postData = paramString;
    }

    public String toString()
    {
        return "PhoneContactsBean{postData='" + this.postData + '\'' + ", localContactList=" + this.localContactList + '}';
    }
}

