package cn.com.ukey;

import org.apache.http.util.ByteArrayBuffer;

public class CcidApduMaker
{
    private static final String TAG = "CcidApduMaker";
    private static int mSeq = 1;

    private static int byteArrayToInt32(byte[] paramArrayOfByte)
    {
        if (paramArrayOfByte.length != 4) {
            return -1;
        }
        return Utility.getUnsignedByte(paramArrayOfByte[0]) + Utility.getUnsignedByte(paramArrayOfByte[1]) * 256 + Utility.getUnsignedByte(paramArrayOfByte[2]) * 256 * 256 + Utility.getUnsignedByte(paramArrayOfByte[3]) * 256 * 256 * 256;
    }

    public static int getSeq()
    {
        int i = mSeq;
        if (mSeq == 255)
        {
            mSeq = 1;
            return i;
        }
        mSeq += 1;
        return i;
    }

    private static byte[] int32ToByteArray(int paramInt)
    {
        return new byte[] { (byte)paramInt, 0, 0, 0 };
    }

    public static byte[] make(byte[] paramArrayOfByte)
    {
        if (paramArrayOfByte == null) {
            return null;
        }
        return make(paramArrayOfByte, paramArrayOfByte.length);
    }

    public static byte[] make(byte[] paramArrayOfByte, int paramInt)
    {
        if ((paramInt > paramArrayOfByte.length) || (paramInt > 255)) {
            return null;
        }
        ByteArrayBuffer localByteArrayBuffer = new ByteArrayBuffer(256);
        localByteArrayBuffer.append(111);
        localByteArrayBuffer.append(int32ToByteArray(paramInt), 0, 4);
        localByteArrayBuffer.append(1);
        localByteArrayBuffer.append((byte)getSeq());
        localByteArrayBuffer.append(0);
        localByteArrayBuffer.append(0);
        localByteArrayBuffer.append(0);
        localByteArrayBuffer.append(paramArrayOfByte, 0, paramInt);
        return localByteArrayBuffer.toByteArray();
    }

    public static CcidResponse parse(byte[] paramArrayOfByte, int paramInt)
    {
        Object localObject;
        if ((paramArrayOfByte == null) || (paramInt > paramArrayOfByte.length)) {
            localObject = null;
        }
        CcidResponse localCcidResponse;
        int i;
        do
        {
            return (CcidResponse)localObject;
            localCcidResponse = new CcidResponse();
            localObject = new byte[4];
            localCcidResponse.mType = Utility.getUnsignedByte(paramArrayOfByte[0]);
            System.arraycopy(paramArrayOfByte, 1, localObject, 0, 4);
            localCcidResponse.mSlot = Utility.getUnsignedByte(paramArrayOfByte[5]);
            localCcidResponse.mSeq = Utility.getUnsignedByte(paramArrayOfByte[6]);
            localCcidResponse.mStatus = Utility.getUnsignedByte(paramArrayOfByte[7]);
            localCcidResponse.mError = Utility.getUnsignedByte(paramArrayOfByte[8]);
            localCcidResponse.mChainParameter = Utility.getUnsignedByte(paramArrayOfByte[9]);
            i = byteArrayToInt32((byte[])localObject);
            if (paramInt != i + 10) {
                return null;
            }
            localObject = localCcidResponse;
        } while (i == 0);
        localCcidResponse.mData = new byte[i];
        System.arraycopy(paramArrayOfByte, 10, localCcidResponse.mData, 0, i);
        return localCcidResponse;
    }

    public static class CcidResponse
    {
        public int mChainParameter;
        public byte[] mData;
        public int mError;
        public int mSeq;
        public int mSlot;
        public int mStatus;
        public int mType;
    }
}
