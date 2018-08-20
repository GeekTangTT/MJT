package com.olym.mjt.module.calllog;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;

public class CallItem
        implements Parcelable
{
    public static final Parcelable.Creator<CallItem> CREATOR = new Parcelable.Creator()
    {
        public CallItem createFromParcel(Parcel paramAnonymousParcel)
        {
            return new CallItem(paramAnonymousParcel);
        }

        public CallItem[] newArray(int paramAnonymousInt)
        {
            return new CallItem[paramAnonymousInt];
        }
    };
    private long data;
    private String displayName;
    private String domain;
    private long duration;
    private ArrayList<ExtraItem> extraItems;
    private String id;
    private String name;
    private String number;
    private int type;

    public CallItem() {}

    public CallItem(int paramInt)
    {
        this.type = paramInt;
    }

    public CallItem(int paramInt, long paramLong1, long paramLong2)
    {
        this.type = paramInt;
        this.data = paramLong1;
        this.duration = paramLong2;
    }

    public CallItem(int paramInt, String paramString1, String paramString2, long paramLong)
    {
        this.type = paramInt;
        this.number = paramString1;
        this.name = paramString2;
        this.data = paramLong;
    }

    public CallItem(int paramInt, String paramString1, String paramString2, long paramLong1, long paramLong2)
    {
        this.type = paramInt;
        this.number = paramString1;
        this.name = paramString2;
        this.data = paramLong1;
        this.duration = paramLong2;
    }

    public CallItem(int paramInt, String paramString1, String paramString2, long paramLong, ArrayList<ExtraItem> paramArrayList)
    {
        this.type = paramInt;
        this.number = paramString1;
        this.name = paramString2;
        this.data = paramLong;
        this.extraItems = paramArrayList;
    }

    protected CallItem(Parcel paramParcel)
    {
        this.type = paramParcel.readInt();
        this.number = paramParcel.readString();
        this.name = paramParcel.readString();
        this.data = paramParcel.readLong();
        this.duration = paramParcel.readLong();
        this.displayName = paramParcel.readString();
        this.extraItems = paramParcel.createTypedArrayList(ExtraItem.CREATOR);
        this.domain = paramParcel.readString();
    }

    public int describeContents()
    {
        return 0;
    }

    public long getData()
    {
        return this.data;
    }

    public String getDisplayName()
    {
        return this.displayName;
    }

    public String getDomain()
    {
        return this.domain;
    }

    public long getDuration()
    {
        return this.duration;
    }

    public ArrayList<ExtraItem> getExtraItems()
    {
        return this.extraItems;
    }

    public String getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public String getNumber()
    {
        return this.number;
    }

    public int getType()
    {
        return this.type;
    }

    public void setData(long paramLong)
    {
        this.data = paramLong;
    }

    public void setDisplayName(String paramString)
    {
        this.displayName = paramString;
    }

    public void setDomain(String paramString)
    {
        this.domain = paramString;
    }

    public void setDuration(long paramLong)
    {
        this.duration = paramLong;
    }

    public void setExtraItems(ArrayList<ExtraItem> paramArrayList)
    {
        this.extraItems = paramArrayList;
    }

    public void setId(String paramString)
    {
        this.id = paramString;
    }

    public void setName(String paramString)
    {
        this.name = paramString;
    }

    public void setNumber(String paramString)
    {
        this.number = paramString;
    }

    public void setType(int paramInt)
    {
        this.type = paramInt;
    }

    public String toString()
    {
        return "CallItem{id='" + this.id + '\'' + ", type=" + this.type + ", number='" + this.number + '\'' + ", name='" + this.name + '\'' + ", data=" + this.data + ", duration=" + this.duration + ", displayName='" + this.displayName + '\'' + ", extraItems=" + this.extraItems + ", domain='" + this.domain + '\'' + '}';
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
        paramParcel.writeInt(this.type);
        paramParcel.writeString(this.number);
        paramParcel.writeString(this.name);
        paramParcel.writeLong(this.data);
        paramParcel.writeLong(this.duration);
        paramParcel.writeString(this.displayName);
        paramParcel.writeTypedList(this.extraItems);
        paramParcel.writeString(this.domain);
    }

    public static class ExtraItem
            implements Parcelable
    {
        public static final Parcelable.Creator<ExtraItem> CREATOR = new Parcelable.Creator()
        {
            public CallItem.ExtraItem createFromParcel(Parcel paramAnonymousParcel)
            {
                return new CallItem.ExtraItem(paramAnonymousParcel);
            }

            public CallItem.ExtraItem[] newArray(int paramAnonymousInt)
            {
                return new CallItem.ExtraItem[paramAnonymousInt];
            }
        };
        private long data;
        private long duration;
        private String id;
        private int type;

        public ExtraItem() {}

        public ExtraItem(int paramInt, long paramLong)
        {
            this.type = paramInt;
            this.data = paramLong;
        }

        public ExtraItem(int paramInt, long paramLong1, long paramLong2)
        {
            this.type = paramInt;
            this.data = paramLong1;
            this.duration = paramLong2;
        }

        protected ExtraItem(Parcel paramParcel)
        {
            this.type = paramParcel.readInt();
            this.data = paramParcel.readLong();
            this.duration = paramParcel.readLong();
        }

        public ExtraItem(String paramString, int paramInt, long paramLong1, long paramLong2)
        {
            this.id = paramString;
            this.type = paramInt;
            this.data = paramLong1;
            this.duration = paramLong2;
        }

        public int describeContents()
        {
            return 0;
        }

        public long getData()
        {
            return this.data;
        }

        public long getDuration()
        {
            return this.duration;
        }

        public String getId()
        {
            return this.id;
        }

        public int getType()
        {
            return this.type;
        }

        public void setData(long paramLong)
        {
            this.data = paramLong;
        }

        public void setDuration(long paramLong)
        {
            this.duration = paramLong;
        }

        public void setId(String paramString)
        {
            this.id = paramString;
        }

        public void setType(int paramInt)
        {
            this.type = paramInt;
        }

        public String toString()
        {
            return "ExtraItem{id='" + this.id + '\'' + ", type=" + this.type + ", data=" + this.data + ", duration=" + this.duration + '}';
        }

        public void writeToParcel(Parcel paramParcel, int paramInt)
        {
            paramParcel.writeInt(this.type);
            paramParcel.writeLong(this.data);
            paramParcel.writeLong(this.duration);
        }
    }
}
