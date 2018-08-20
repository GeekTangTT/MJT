package com.olym.mjt.pjsip.sip.event;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.olym.mjt.utils.EventBusUtil;

public class CallEvent
        implements Parcelable
{
    public static final Parcelable.Creator<CallEvent> CREATOR = new Parcelable.Creator()
    {
        public CallEvent createFromParcel(Parcel paramAnonymousParcel)
        {
            return new CallEvent(paramAnonymousParcel);
        }

        public CallEvent[] newArray(int paramAnonymousInt)
        {
            return new CallEvent[paramAnonymousInt];
        }
    };
    private Bundle bundle = null;
    private String callee;
    private String domain = null;
    private boolean reCall = false;
    private String userId;
    private String version = null;

    protected CallEvent(Parcel paramParcel)
    {
        this.callee = paramParcel.readString();
        this.bundle = paramParcel.readBundle();
        if (paramParcel.readByte() != 0) {
            bool = true;
        }
        this.reCall = bool;
        this.domain = paramParcel.readString();
        this.version = paramParcel.readString();
        this.userId = paramParcel.readString();
    }

    public CallEvent(String paramString)
    {
        this.callee = paramString;
        this.reCall = false;
    }

    public CallEvent(String paramString, Bundle paramBundle)
    {
        this.callee = paramString;
        this.bundle = paramBundle;
    }

    public CallEvent(String paramString1, String paramString2, String paramString3)
    {
        this.callee = paramString1;
        this.domain = paramString2;
        this.version = paramString3;
    }

    public CallEvent(String paramString1, String paramString2, String paramString3, String paramString4)
    {
        this.callee = paramString1;
        this.domain = paramString2;
        this.version = paramString3;
        this.userId = paramString4;
    }

    public CallEvent(String paramString, boolean paramBoolean)
    {
        this.callee = paramString;
        this.reCall = paramBoolean;
    }

    public CallEvent(String paramString1, boolean paramBoolean, String paramString2, String paramString3)
    {
        this.callee = paramString1;
        this.reCall = paramBoolean;
        this.domain = paramString2;
        this.version = paramString3;
    }

    public static void post(CallEvent paramCallEvent)
    {
        EventBusUtil.post(paramCallEvent);
    }

    public int describeContents()
    {
        return 0;
    }

    public Bundle getBundle()
    {
        return this.bundle;
    }

    public String getCallee()
    {
        return this.callee;
    }

    public String getDomain()
    {
        return this.domain;
    }

    public String getUserId()
    {
        return this.userId;
    }

    public String getVersion()
    {
        return this.version;
    }

    public boolean isReCall()
    {
        return this.reCall;
    }

    public boolean isSystemCall()
    {
        return (this.callee.equals("10000")) || (this.callee.equals("10001")) || (this.callee.equals("10002")) || (this.callee.equals("10004")) || (this.callee.equals("10005")) || (this.callee.equals("pushuser007")) || (this.callee.equals("10006"));
    }

    public void setBundle(Bundle paramBundle)
    {
        this.bundle = paramBundle;
    }

    public void setCallee(String paramString)
    {
        this.callee = paramString;
    }

    public void setDomain(String paramString)
    {
        this.domain = paramString;
    }

    public void setReCall(boolean paramBoolean)
    {
        this.reCall = paramBoolean;
    }

    public void setUserId(String paramString)
    {
        this.userId = paramString;
    }

    public void setVersion(String paramString)
    {
        this.version = paramString;
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
        paramParcel.writeString(this.callee);
        paramParcel.writeBundle(this.bundle);
        if (this.reCall) {}
        for (byte b = 1;; b = 0)
        {
            paramParcel.writeByte(b);
            paramParcel.writeString(this.domain);
            paramParcel.writeString(this.version);
            paramParcel.writeString(this.userId);
            return;
        }
    }
}

