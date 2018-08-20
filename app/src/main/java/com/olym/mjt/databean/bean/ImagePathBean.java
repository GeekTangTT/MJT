package com.olym.mjt.databean.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ImagePathBean
        implements Parcelable
{
    public static final Parcelable.Creator<ImagePathBean> CREATOR = new Parcelable.Creator()
    {
        public ImagePathBean createFromParcel(Parcel paramAnonymousParcel)
        {
            ImagePathBean localImagePathBean = new ImagePathBean(0, "", 0);
            ImagePathBean.access$002(localImagePathBean, paramAnonymousParcel.readInt());
            ImagePathBean.access$102(localImagePathBean, paramAnonymousParcel.readString());
            ImagePathBean.access$202(localImagePathBean, paramAnonymousParcel.readInt());
            return localImagePathBean;
        }

        public ImagePathBean[] newArray(int paramAnonymousInt)
        {
            return new ImagePathBean[paramAnonymousInt];
        }
    };
    private String filePath;
    private int index;
    private int isMySend;

    public ImagePathBean(int paramInt1, String paramString, int paramInt2)
    {
        this.index = paramInt1;
        this.filePath = paramString;
        this.isMySend = paramInt2;
    }

    public int describeContents()
    {
        return 0;
    }

    public String getFilePath()
    {
        return this.filePath;
    }

    public int getIndex()
    {
        return this.index;
    }

    public int getIsMySend()
    {
        return this.isMySend;
    }

    public void setFilePath(String paramString)
    {
        this.filePath = paramString;
    }

    public void setIndex(int paramInt)
    {
        this.index = paramInt;
    }

    public void setIsMySend(int paramInt)
    {
        this.isMySend = paramInt;
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
        paramParcel.writeInt(this.index);
        paramParcel.writeString(this.filePath);
        paramParcel.writeInt(this.isMySend);
    }
}
