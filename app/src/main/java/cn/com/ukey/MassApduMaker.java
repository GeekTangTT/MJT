package cn.com.ukey;

import java.util.Arrays;
import org.apache.http.util.ByteArrayBuffer;

public class MassApduMaker
{
    private static final byte[] CBWCB_BULK_RECV;
    private static final byte[] CBWCB_BULK_SEND;
    private static final byte[] CBW_TAG = { 17, 17, 17, 17 };
    private static final byte[] PREFIX_CBW;
    private static final byte[] PREFIX_CSW;
    private static final String TAG = "MassApduMaker";

    static
    {
        byte[] arrayOfByte = new byte[6];
        arrayOfByte[0] = -1;
        arrayOfByte[1] = 1;
        arrayOfByte[4] = 29;
        arrayOfByte[5] = 4;
        CBWCB_BULK_SEND = arrayOfByte;
        arrayOfByte = new byte[6];
        arrayOfByte[0] = -1;
        arrayOfByte[1] = 2;
        arrayOfByte[4] = 29;
        arrayOfByte[5] = 4;
        CBWCB_BULK_RECV = arrayOfByte;
        PREFIX_CBW = new byte[] { 85, 83, 66, 67 };
        PREFIX_CSW = new byte[] { 85, 83, 66, 83 };
    }

    private static int byteArrayToInt32(byte[] paramArrayOfByte)
    {
        if (paramArrayOfByte.length != 4) {
            return -1;
        }
        return Utility.getUnsignedByte(paramArrayOfByte[0]);
    }

    private static byte[] int32ToByteArray(int paramInt)
    {
        return new byte[] { (byte)paramInt, 0, 0, 0 };
    }

    public static byte[] make(boolean paramBoolean, int paramInt)
    {
        if (paramInt > 255) {
            return null;
        }
        ByteArrayBuffer localByteArrayBuffer = new ByteArrayBuffer(256);
        localByteArrayBuffer.append(PREFIX_CBW, 0, PREFIX_CBW.length);
        localByteArrayBuffer.append(CBW_TAG, 0, CBW_TAG.length);
        byte[] arrayOfByte = int32ToByteArray(paramInt);
        localByteArrayBuffer.append(arrayOfByte, 0, arrayOfByte.length);
        if (paramBoolean)
        {
            localByteArrayBuffer.append(0);
            localByteArrayBuffer.append(0);
            localByteArrayBuffer.append(6);
            if (!paramBoolean) {
                break label146;
            }
            localByteArrayBuffer.append(CBWCB_BULK_SEND, 0, CBWCB_BULK_SEND.length);
        }
        for (;;)
        {
            localByteArrayBuffer.append(new byte[10], 0, 10);
            MyLog.i("MassApduMaker", "CSW = " + Utility.bytesToHexString(localByteArrayBuffer.toByteArray()));
            return localByteArrayBuffer.toByteArray();
            localByteArrayBuffer.append(128);
            break;
            label146:
            localByteArrayBuffer.append(CBWCB_BULK_RECV, 0, CBWCB_BULK_RECV.length);
        }
    }

    public static MassCSW parse(byte[] paramArrayOfByte, int paramInt)
    {
        if ((paramArrayOfByte == null) || (paramInt > paramArrayOfByte.length) || (paramInt != 13))
        {
            MyLog.e("MassApduMaker", "Invalid masstorage CSW. ");
            return null;
        }
        MassCSW localMassCSW = new MassCSW();
        System.arraycopy(paramArrayOfByte, 0, localMassCSW.dCBWSignature, 0, 4);
        if (!Arrays.equals(localMassCSW.dCBWSignature, PREFIX_CSW))
        {
            MyLog.e("MassApduMaker", "Invalid masstorage CSW. " + Utility.bytesToHexString(paramArrayOfByte));
            return null;
        }
        System.arraycopy(paramArrayOfByte, 4, localMassCSW.dCBWTag, 0, 4);
        byte[] arrayOfByte = new byte[4];
        System.arraycopy(paramArrayOfByte, 8, arrayOfByte, 0, 4);
        localMassCSW.iDataResidue = byteArrayToInt32(arrayOfByte);
        localMassCSW.bCSWStatus = Utility.getUnsignedByte(paramArrayOfByte[12]);
        return localMassCSW;
    }

    public static class MassCSW
    {
        public int bCSWStatus = 0;
        public byte[] dCBWSignature = new byte[4];
        public byte[] dCBWTag = new byte[4];
        public int iDataResidue = 0;
    }
}

