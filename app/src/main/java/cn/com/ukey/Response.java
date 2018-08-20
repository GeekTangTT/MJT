package cn.com.ukey;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Response
        implements Parcelable
{
    public static final Parcelable.Creator<Response> CREATOR = new Parcelable.Creator()
    {
        public Response createFromParcel(Parcel paramAnonymousParcel)
        {
            return new Response(paramAnonymousParcel, null);
        }

        public Response[] newArray(int paramAnonymousInt)
        {
            return new Response[paramAnonymousInt];
        }
    };
    private String mResponseApdu = "ffff";
    private int mReturnCode = 65535;

    public Response() {}

    private Response(Parcel paramParcel)
    {
        readFromParcel(paramParcel);
    }

    public int describeContents()
    {
        return 0;
    }

    public String getResponseApdu()
    {
        return this.mResponseApdu + Integer.toHexString(this.mReturnCode);
    }

    public int getReturnCode()
    {
        return this.mReturnCode;
    }

    public void readFromParcel(Parcel paramParcel)
    {
        this.mReturnCode = paramParcel.readInt();
        this.mResponseApdu = paramParcel.readString();
    }

    public void setResponseApdu(String paramString)
    {
        this.mResponseApdu = paramString;
    }

    public void setReturnCode(int paramInt)
    {
        this.mReturnCode = paramInt;
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
        paramParcel.writeInt(this.mReturnCode);
        paramParcel.writeString(this.mResponseApdu);
    }
}
