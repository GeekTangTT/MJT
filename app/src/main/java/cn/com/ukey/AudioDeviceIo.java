package cn.com.ukey;

import android.content.Context;
import android.os.SystemClock;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.spec.RSAPublicKeySpec;
import org.apache.http.util.ByteArrayBuffer;

public class AudioDeviceIo
        implements DeviceIoInterface
{
    private String TAG = "AudioDeviceIo";
    private boolean bPowerDownSuccess = false;
    private boolean bPowerUpSuccess = false;
    private boolean bRecvResponseSuccess = false;
    private Double iBatteryVoltageSuccess = Double.valueOf(0.0D);
    private IUKeyResponseCallback mCallback;
    protected byte[] mChannelKey = null;
    private Context mContext;
    public int mCosType = 3;
    private boolean mHasDataDetected = false;
    private long mLastDataDetectedTime = 0L;
    private int mLastError = 0;
    protected byte[] mProtectKey = new byte[16];
    private int mRetryTimes = 0;
    private int mTimeoutInSecond = 3;
    protected String strResponse = "";

    private void getAutoConfigAndStartUCapController() {}

    private int getLastError()
    {
        int i = this.mLastError;
        setLastError(0);
        return i;
    }

    private int resendCommand(String paramString, StringBuffer paramStringBuffer, int paramInt)
    {
        if (!waitForIdle()) {
            return 65234;
        }
        MyLog.e("TAG", "���������" + paramInt + "���");
        SystemClock.uptimeMillis();
        this.bRecvResponseSuccess = false;
        return 0;
    }

    private void setLastError(int paramInt)
    {
        try
        {
            this.mLastError = paramInt;
            if ((this.mCallback != null) && (this.mLastError != 0)) {
                this.mCallback.onError(paramInt);
            }
            return;
        }
        finally {}
    }

    private boolean waitForIdle()
    {
        return false;
    }

    protected boolean RsaVerifySignature(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
    {
        if ((paramArrayOfByte1 == null) || (paramArrayOfByte2 == null) || (paramArrayOfByte3 == null)) {
            return false;
        }
        paramArrayOfByte3 = Utility.bytesToHexString(byteReverse(paramArrayOfByte3));
        try
        {
            paramArrayOfByte3 = new RSAPublicKeySpec(new BigInteger(paramArrayOfByte3, 16), new BigInteger("65537"));
            paramArrayOfByte3 = KeyFactory.getInstance("RSA").generatePublic(paramArrayOfByte3);
            paramArrayOfByte1 = byteReverse(paramArrayOfByte1);
            paramArrayOfByte2 = byteReverse(paramArrayOfByte2);
            Signature localSignature = Signature.getInstance("SHA1withRSA");
            localSignature.initVerify(paramArrayOfByte3);
            localSignature.update(paramArrayOfByte2);
            boolean bool = localSignature.verify(paramArrayOfByte1);
            return bool;
        }
        catch (Exception paramArrayOfByte1)
        {
            paramArrayOfByte1.printStackTrace();
        }
        return false;
    }

    public int autoConfig(Context paramContext, IUKeyResponseCallback paramIUKeyResponseCallback)
    {
        MyLog.i(this.TAG, "autoConfig");
        this.mContext = paramContext;
        this.mCallback = paramIUKeyResponseCallback;
        return 0;
    }

    protected byte[] byteReverse(byte[] paramArrayOfByte)
    {
        ByteArrayBuffer localByteArrayBuffer = new ByteArrayBuffer(128);
        int i = 0;
        for (;;)
        {
            if (i >= paramArrayOfByte.length) {
                return localByteArrayBuffer.toByteArray();
            }
            localByteArrayBuffer.append(paramArrayOfByte, paramArrayOfByte.length - 1 - i, 1);
            i += 1;
        }
    }

    public void clear()
    {
        MyLog.i(this.TAG, "finalize");
        this.mCallback = null;
    }

    public int connect()
    {
        return 0;
    }

    public int connect(String paramString)
    {
        return 0;
    }

    public int disconnect()
    {
        this.mCallback = null;
        return 36864;
    }

    public double getBatteryVoltage()
    {
        return 4.0D;
    }

    protected byte[] getChannelKeyPlain()
    {
        Object localObject;
        if (this.mChannelKey == null)
        {
            localObject = null;
            return (byte[])localObject;
        }
        byte[] arrayOfByte = new byte[16];
        int i = 0;
        for (;;)
        {
            localObject = arrayOfByte;
            if (i >= 16) {
                break;
            }
            arrayOfByte[i] = ((byte)(this.mChannelKey[i] ^ this.mProtectKey[i]));
            i += 1;
        }
    }

    public int getCosType()
    {
        return this.mCosType;
    }

    protected String getResponse()
    {
        String str = this.strResponse;
        this.strResponse = "";
        return str;
    }

    public void init(Context paramContext, IUKeyResponseCallback paramIUKeyResponseCallback)
    {
        MyLog.i(this.TAG, "initialize");
        this.mContext = paramContext;
        this.mCallback = paramIUKeyResponseCallback;
        this.mLastDataDetectedTime = 0L;
    }

    public boolean isKeyPresent()
    {
        return false;
    }

    protected int sendCommand(String paramString, StringBuffer paramStringBuffer)
    {
        if (!waitForIdle()) {
            return 65234;
        }
        Utility.sendBroadcastForTest(this.mContext, "send: " + paramString);
        SystemClock.uptimeMillis();
        this.bRecvResponseSuccess = false;
        return 0;
    }

    public int sendCommand(byte[] paramArrayOfByte, ByteArrayBuffer paramByteArrayBuffer)
    {
        return 0;
    }

    public Response sendCommand(String paramString)
    {
        return new Response();
    }

    protected Response sendCommandForChannel(String paramString)
    {
        Response localResponse = new Response();
        StringBuffer localStringBuffer = new StringBuffer();
        int i = sendCommand(paramString, localStringBuffer);
        if (i != 36864)
        {
            localResponse.setReturnCode(i);
            return localResponse;
        }
        if ((localStringBuffer.length() % 2 != 0) || (localStringBuffer.length() < 4))
        {
            localResponse.setReturnCode(65185);
            return localResponse;
        }
        int m = localStringBuffer.length() - 4;
        int k = Integer.parseInt(localStringBuffer.substring(m, m + 2), 16);
        int j = Integer.parseInt(localStringBuffer.substring(m + 2, m + 4), 16);
        i = k;
        if (k == 97)
        {
            paramString = "00C00000" + localStringBuffer.substring(m + 2, m + 4);
            localStringBuffer.delete(0, localStringBuffer.length());
            if ((sendCommand(paramString, localStringBuffer) != 36864) || (localStringBuffer.length() < 4))
            {
                localResponse.setReturnCode(65185);
                return localResponse;
            }
            j = localStringBuffer.length() - 4;
            i = Integer.parseInt(localStringBuffer.substring(j, j + 2), 16);
            j = Integer.parseInt(localStringBuffer.substring(j + 2, j + 4), 16);
        }
        paramString = new ByteArrayBuffer(256);
        k = 0;
        for (;;)
        {
            if (k >= localStringBuffer.length() - 4)
            {
                localResponse.setResponseApdu(Utility.bytesToHexString(paramString.toByteArray(), paramString.length()));
                localResponse.setReturnCode(i * 256 + j);
                return localResponse;
            }
            paramString.append(Integer.parseInt(localStringBuffer.substring(k, k + 2), 16));
            k += 2;
        }
    }

    public void setCallback(IUKeyResponseCallback paramIUKeyResponseCallback)
    {
        this.mCallback = paramIUKeyResponseCallback;
    }
}
