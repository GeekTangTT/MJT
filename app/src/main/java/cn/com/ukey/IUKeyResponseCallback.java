package cn.com.ukey;

public abstract interface IUKeyResponseCallback
{
    public abstract void onAutoConfigProgressUpdate(int paramInt);

    public abstract void onAutoConfigResult(boolean paramBoolean, String paramString);

    public abstract void onDeviceChanged(boolean paramBoolean);

    public abstract void onError(int paramInt);

    public abstract void onLowBatteryVoltage();
}
