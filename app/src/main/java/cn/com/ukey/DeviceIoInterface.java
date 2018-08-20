package cn.com.ukey;

import android.content.Context;

public abstract interface DeviceIoInterface
{
    public static final byte[] mPublicKey = new byte['��'];

    public abstract void clear();

    public abstract int connect();

    public abstract int disconnect();

    public abstract double getBatteryVoltage();

    public abstract int getCosType();

    public abstract void init(Context paramContext, IUKeyResponseCallback paramIUKeyResponseCallback);

    public abstract boolean isKeyPresent();

    public abstract Response sendCommand(String paramString);

    public abstract void setCallback(IUKeyResponseCallback paramIUKeyResponseCallback);

    public static abstract interface COS_TYPE
    {
        public static final int COS_TYPE_EAL3 = 2;
        public static final int COS_TYPE_EAL4 = 3;
        public static final int COS_TYPE_OLD = 1;
    }
}
