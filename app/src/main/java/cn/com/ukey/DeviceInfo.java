package cn.com.ukey;

public class DeviceInfo
{
    public static int KEY_TYPE_NORMAL = 0;
    public static int KEY_TYPE_SM2_RSA_USE_SHA1 = 1;
    public static int KEY_TYPE_SM2_RSA_USE_SHA256SM3 = 2;
    public boolean bSupportOverturnScreen = false;
    public byte[] cosversion = new byte[8];
    public boolean isDefaultPin = false;
    public int keyType = 0;
    public int lifeCycle = 0;
    public String sn = "";

    public void clear()
    {
        this.sn = "";
        this.cosversion[0] = 0;
        this.cosversion[1] = 0;
        this.cosversion[2] = 0;
        this.cosversion[3] = 0;
        this.isDefaultPin = false;
        this.keyType = 0;
        this.bSupportOverturnScreen = false;
    }

    public boolean isEmpty()
    {
        if (this.sn.equals("")) {}
        while ((this.cosversion[0] == 0) && (this.cosversion[1] == 0) && (this.cosversion[2] == 0) && (this.cosversion[3] == 0)) {
            return true;
        }
        return false;
    }
}
