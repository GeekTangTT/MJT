package com.olym.mjt.databean.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.alibaba.fastjson.annotation.JSONField;

public class MucRoomMember
        implements Parcelable
{
    public static final Parcelable.Creator<MucRoomMember> CREATOR = new MucRoomMember.1();
    private int active;
    @JSONField(name="nickname")
    private String nickName;
    private int role;
    private int sub;
    private int talkTime;
    private String userId;

    public MucRoomMember() {}

    protected MucRoomMember(Parcel paramParcel)
    {
        this.userId = paramParcel.readString();
        this.nickName = paramParcel.readString();
        this.role = paramParcel.readInt();
        this.talkTime = paramParcel.readInt();
        this.active = paramParcel.readInt();
        this.sub = paramParcel.readInt();
    }

    public int describeContents()
    {
        return 0;
    }

    public int getActive()
    {
        return this.active;
    }

    public String getNickName()
    {
        return this.nickName;
    }

    public int getRole()
    {
        return this.role;
    }

    public int getSub()
    {
        return this.sub;
    }

    public int getTalkTime()
    {
        return this.talkTime;
    }

    public String getUserId()
    {
        return this.userId;
    }

    public void setActive(int paramInt)
    {
        this.active = paramInt;
    }

    public void setNickName(String paramString)
    {
        this.nickName = paramString;
    }

    public void setRole(int paramInt)
    {
        this.role = paramInt;
    }

    public void setSub(int paramInt)
    {
        this.sub = paramInt;
    }

    public void setTalkTime(int paramInt)
    {
        this.talkTime = paramInt;
    }

    public void setUserId(String paramString)
    {
        this.userId = paramString;
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
        paramParcel.writeString(this.userId);
        paramParcel.writeString(this.nickName);
        paramParcel.writeInt(this.role);
        paramParcel.writeInt(this.talkTime);
        paramParcel.writeInt(this.active);
        paramParcel.writeInt(this.sub);
    }
}
