package com.olym.mjt.utils.cipher;

import com.lc.methodex.LogFinalUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Arrays;

public class InternalHash
{
    private static final byte[] FirstPadding = { Byte.MIN_VALUE };
    private static final BigInteger IV;
    private static final Integer Tj15;
    private static final Integer Tj63;
    private static final byte[] ZeroPadding = { 0 };
    private static char[] chars = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70 };
    private static final String ivHexStr = "7380166f 4914b2b9 172442d7 da8a0600 a96f30bc 163138ac e38dee4d b0fb0e4e";
    byte[] cipher = new byte[16];
    byte[] plain = new byte[16];

    static
    {
        IV = new BigInteger("7380166f 4914b2b9 172442d7 da8a0600 a96f30bc 163138ac e38dee4d b0fb0e4e".replaceAll(" ", ""), 16);
        Tj15 = Integer.valueOf("79cc4520", 16);
        Tj63 = Integer.valueOf("7a879d8d", 16);
    }

    private static byte[] CF(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
            throws IOException
    {
        int k = toInteger(paramArrayOfByte1, 0);
        int i4 = toInteger(paramArrayOfByte1, 1);
        int m = toInteger(paramArrayOfByte1, 2);
        int n = toInteger(paramArrayOfByte1, 3);
        int i5 = toInteger(paramArrayOfByte1, 4);
        int i3 = toInteger(paramArrayOfByte1, 5);
        int j = toInteger(paramArrayOfByte1, 6);
        int i1 = toInteger(paramArrayOfByte1, 7);
        int[] arrayOfInt1 = new int[68];
        int[] arrayOfInt2 = new int[64];
        int i = 0;
        while (i < 16)
        {
            arrayOfInt1[i] = toInteger(paramArrayOfByte2, i);
            i += 1;
        }
        i = 16;
        while (i < 68)
        {
            arrayOfInt1[i] = (P1(Integer.valueOf(arrayOfInt1[(i - 16)] ^ arrayOfInt1[(i - 9)] ^ Integer.rotateLeft(arrayOfInt1[(i - 3)], 15))).intValue() ^ Integer.rotateLeft(arrayOfInt1[(i - 13)], 7) ^ arrayOfInt1[(i - 6)]);
            i += 1;
        }
        i = 0;
        while (i < 64)
        {
            arrayOfInt1[i] ^= arrayOfInt1[(i + 4)];
            i += 1;
        }
        int i2 = 0;
        i = i5;
        for (;;)
        {
            i5 = i1;
            i1 = n;
            if (i2 >= 64) {
                break;
            }
            int i6 = Integer.rotateLeft(Integer.rotateLeft(k, 12) + i + Integer.rotateLeft(T(i2), i2), 7);
            int i9 = Integer.rotateLeft(k, 12);
            int i10 = FF(Integer.valueOf(k), Integer.valueOf(i4), Integer.valueOf(m), i2).intValue();
            int i11 = arrayOfInt2[i2];
            int i7 = GG(Integer.valueOf(i), Integer.valueOf(i3), Integer.valueOf(j), i2).intValue();
            int i8 = arrayOfInt1[i2];
            n = m;
            m = Integer.rotateLeft(i4, 9);
            i4 = k;
            k = i10 + i1 + (i6 ^ i9) + i11;
            i1 = j;
            j = Integer.rotateLeft(i3, 19);
            i3 = i;
            i = P0(Integer.valueOf(i7 + i5 + i6 + i8)).intValue();
            i2 += 1;
        }
        paramArrayOfByte2 = toByteArray(k, i4, m, i1, i, i3, j, i5);
        i = 0;
        while (i < paramArrayOfByte2.length)
        {
            paramArrayOfByte2[i] = ((byte)(paramArrayOfByte2[i] ^ paramArrayOfByte1[i]));
            i += 1;
        }
        return paramArrayOfByte2;
    }

    private static Integer FF(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, int paramInt)
    {
        if ((paramInt >= 0) && (paramInt <= 15)) {
            return Integer.valueOf(paramInteger1.intValue() ^ paramInteger2.intValue() ^ paramInteger3.intValue());
        }
        if ((paramInt >= 16) && (paramInt <= 63)) {
            return Integer.valueOf(paramInteger1.intValue() & paramInteger2.intValue() | paramInteger1.intValue() & paramInteger3.intValue() | paramInteger2.intValue() & paramInteger3.intValue());
        }
        throw new RuntimeException("data invalid");
    }

    private static Integer GG(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, int paramInt)
    {
        if ((paramInt >= 0) && (paramInt <= 15)) {
            return Integer.valueOf(paramInteger1.intValue() ^ paramInteger2.intValue() ^ paramInteger3.intValue());
        }
        if ((paramInt >= 16) && (paramInt <= 63)) {
            return Integer.valueOf(paramInteger1.intValue() & paramInteger2.intValue() | (paramInteger1.intValue() ^ 0xFFFFFFFF) & paramInteger3.intValue());
        }
        throw new RuntimeException("data invalid");
    }

    public static byte[] InternalHash(byte[] paramArrayOfByte, int paramInt)
    {
        Object localObject = new byte[32];
        setKey((byte[])localObject, paramInt);
        int i = 0;
        for (;;)
        {
            if (i >= paramInt * 16) {
                return localObject;
            }
            try
            {
                byte[] arrayOfByte = hash(byteMerger(paramArrayOfByte, (byte[])localObject));
                localObject = arrayOfByte;
            }
            catch (IOException localIOException)
            {
                for (;;)
                {
                    LogFinalUtils.logForException(localIOException);
                    localIOException.printStackTrace();
                }
            }
            i += 1;
        }
        return (byte[])localObject;
    }

    private static Integer P0(Integer paramInteger)
    {
        return Integer.valueOf(paramInteger.intValue() ^ Integer.rotateLeft(paramInteger.intValue(), 9) ^ Integer.rotateLeft(paramInteger.intValue(), 17));
    }

    private static Integer P1(Integer paramInteger)
    {
        return Integer.valueOf(paramInteger.intValue() ^ Integer.rotateLeft(paramInteger.intValue(), 15) ^ Integer.rotateLeft(paramInteger.intValue(), 23));
    }

    private static int T(int paramInt)
    {
        if ((paramInt >= 0) && (paramInt <= 15)) {
            return Tj15.intValue();
        }
        if ((paramInt >= 16) && (paramInt <= 63)) {
            return Tj63.intValue();
        }
        throw new RuntimeException("data invalid");
    }

    public static byte[] byteMerger(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    {
        byte[] arrayOfByte = new byte[paramArrayOfByte1.length + paramArrayOfByte2.length];
        System.arraycopy(paramArrayOfByte1, 0, arrayOfByte, 0, paramArrayOfByte1.length);
        System.arraycopy(paramArrayOfByte2, 0, arrayOfByte, paramArrayOfByte1.length, paramArrayOfByte2.length);
        return arrayOfByte;
    }

    public static byte[] hash(byte[] paramArrayOfByte)
            throws IOException
    {
        byte[] arrayOfByte2 = padding(paramArrayOfByte);
        int j = arrayOfByte2.length / 64;
        paramArrayOfByte = IV.toByteArray();
        byte[] arrayOfByte1 = null;
        int i = 0;
        while (i < j)
        {
            arrayOfByte1 = CF(paramArrayOfByte, Arrays.copyOfRange(arrayOfByte2, i * 64, (i + 1) * 64));
            paramArrayOfByte = arrayOfByte1;
            i += 1;
        }
        return arrayOfByte1;
    }

    private static byte[] long2bytes(long paramLong)
    {
        byte[] arrayOfByte = new byte[8];
        int i = 0;
        while (i < 8)
        {
            arrayOfByte[i] = ((byte)(int)(paramLong >>> (7 - i) * 8));
            i += 1;
        }
        return arrayOfByte;
    }

    private static byte[] padding(byte[] paramArrayOfByte)
            throws IOException
    {
        if (paramArrayOfByte.length >= 2305843009213693952L) {
            throw new RuntimeException("src data invalid.");
        }
        long l3 = paramArrayOfByte.length * 8;
        long l2 = 448L - (1L + l3) % 512L;
        long l1 = l2;
        if (l2 < 0L) {
            l1 = l2 + 512L;
        }
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        localByteArrayOutputStream.write(paramArrayOfByte);
        localByteArrayOutputStream.write(FirstPadding);
        for (l1 -= 7L; l1 > 0L; l1 -= 8L) {
            localByteArrayOutputStream.write(ZeroPadding);
        }
        localByteArrayOutputStream.write(long2bytes(l3));
        return localByteArrayOutputStream.toByteArray();
    }

    private static void printIntArray(int[] paramArrayOfInt, int paramInt)
    {
        int i = 0;
        while (i < paramArrayOfInt.length)
        {
            byte[] arrayOfByte = toByteArray(paramArrayOfInt[i]);
            int j = 0;
            while (j < arrayOfByte.length)
            {
                System.out.print(chars[((arrayOfByte[j] & 0xFF) >> 4)]);
                System.out.print(chars[(arrayOfByte[j] & 0xF)]);
                j += 1;
            }
            if (i % paramInt == paramInt - 1) {}
            i += 1;
        }
    }

    private static void setKey(byte[] paramArrayOfByte, int paramInt)
    {
        paramArrayOfByte = new byte[32];
        switch (paramInt)
        {
            default:
                paramArrayOfByte[29] = 19;
                paramArrayOfByte[10] = 4;
                paramArrayOfByte[24] = 30;
                paramArrayOfByte[25] = 6;
                paramArrayOfByte[11] = 3;
                paramArrayOfByte[12] = 21;
                paramArrayOfByte[4] = 26;
                paramArrayOfByte[5] = 27;
                paramArrayOfByte[0] = 29;
                paramArrayOfByte[1] = 1;
                paramArrayOfByte[30] = 9;
                paramArrayOfByte[31] = 10;
                paramArrayOfByte[21] = 11;
                paramArrayOfByte[22] = 13;
                paramArrayOfByte[6] = 24;
                paramArrayOfByte[15] = 32;
                paramArrayOfByte[23] = 16;
                paramArrayOfByte[7] = 25;
                paramArrayOfByte[8] = 23;
                paramArrayOfByte[16] = 18;
                paramArrayOfByte[2] = 28;
                paramArrayOfByte[3] = 2;
                paramArrayOfByte[17] = 17;
                paramArrayOfByte[18] = 5;
                paramArrayOfByte[19] = 16;
                paramArrayOfByte[20] = 15;
                paramArrayOfByte[27] = 7;
                paramArrayOfByte[28] = 31;
                paramArrayOfByte[9] = 22;
                paramArrayOfByte[13] = 20;
                paramArrayOfByte[14] = 8;
                paramArrayOfByte[26] = 14;
        }
        for (;;)
        {
            return;
            paramArrayOfByte[8] = 4;
            paramArrayOfByte[9] = 31;
            paramArrayOfByte[10] = 22;
            paramArrayOfByte[30] = 10;
            paramArrayOfByte[31] = 9;
            paramArrayOfByte[11] = 21;
            paramArrayOfByte[12] = 23;
            paramArrayOfByte[13] = 24;
            paramArrayOfByte[18] = 5;
            paramArrayOfByte[23] = 14;
            paramArrayOfByte[24] = 12;
            paramArrayOfByte[25] = 7;
            paramArrayOfByte[26] = 15;
            paramArrayOfByte[2] = 29;
            paramArrayOfByte[3] = 30;
            paramArrayOfByte[4] = 28;
            paramArrayOfByte[5] = 2;
            paramArrayOfByte[27] = 8;
            paramArrayOfByte[19] = 18;
            paramArrayOfByte[20] = 16;
            paramArrayOfByte[0] = 1;
            paramArrayOfByte[1] = 3;
            paramArrayOfByte[6] = 27;
            paramArrayOfByte[7] = 26;
            paramArrayOfByte[21] = 6;
            paramArrayOfByte[22] = 13;
            paramArrayOfByte[14] = 25;
            paramArrayOfByte[15] = 19;
            paramArrayOfByte[16] = 20;
            paramArrayOfByte[17] = 17;
            paramArrayOfByte[28] = 11;
            paramArrayOfByte[29] = 32;
            continue;
            paramArrayOfByte[4] = 30;
            paramArrayOfByte[5] = 29;
            paramArrayOfByte[6] = 3;
            paramArrayOfByte[20] = 19;
            paramArrayOfByte[21] = 8;
            paramArrayOfByte[12] = 25;
            paramArrayOfByte[25] = 11;
            paramArrayOfByte[31] = 17;
            paramArrayOfByte[7] = 26;
            paramArrayOfByte[8] = 4;
            paramArrayOfByte[9] = 27;
            paramArrayOfByte[15] = 24;
            paramArrayOfByte[16] = 7;
            paramArrayOfByte[17] = 22;
            paramArrayOfByte[18] = 21;
            paramArrayOfByte[10] = 5;
            paramArrayOfByte[0] = 1;
            paramArrayOfByte[1] = 2;
            paramArrayOfByte[13] = 6;
            paramArrayOfByte[28] = 16;
            paramArrayOfByte[29] = 15;
            paramArrayOfByte[30] = 14;
            paramArrayOfByte[24] = 9;
            paramArrayOfByte[2] = 32;
            paramArrayOfByte[3] = 31;
            paramArrayOfByte[11] = 28;
            paramArrayOfByte[14] = 23;
            paramArrayOfByte[19] = 20;
            paramArrayOfByte[26] = 12;
            paramArrayOfByte[27] = 13;
            paramArrayOfByte[22] = 18;
            paramArrayOfByte[23] = 10;
            continue;
            paramArrayOfByte[3] = 30;
            paramArrayOfByte[1] = 29;
            paramArrayOfByte[2] = 28;
            paramArrayOfByte[9] = 24;
            paramArrayOfByte[31] = 13;
            paramArrayOfByte[19] = 18;
            paramArrayOfByte[6] = 3;
            paramArrayOfByte[7] = 26;
            paramArrayOfByte[8] = 25;
            paramArrayOfByte[4] = 27;
            paramArrayOfByte[5] = 2;
            paramArrayOfByte[14] = 5;
            paramArrayOfByte[15] = 21;
            paramArrayOfByte[16] = 20;
            paramArrayOfByte[17] = 19;
            paramArrayOfByte[0] = 1;
            paramArrayOfByte[10] = 31;
            paramArrayOfByte[18] = 6;
            paramArrayOfByte[24] = 9;
            paramArrayOfByte[25] = 16;
            paramArrayOfByte[26] = 14;
            paramArrayOfByte[27] = 15;
            paramArrayOfByte[28] = 10;
            paramArrayOfByte[29] = 11;
            paramArrayOfByte[30] = 12;
            paramArrayOfByte[20] = 17;
            paramArrayOfByte[21] = 7;
            paramArrayOfByte[22] = 8;
            paramArrayOfByte[11] = 4;
            paramArrayOfByte[12] = 22;
            paramArrayOfByte[13] = 23;
            paramArrayOfByte[23] = 32;
            continue;
            paramArrayOfByte[5] = 27;
            paramArrayOfByte[6] = 25;
            paramArrayOfByte[27] = 7;
            paramArrayOfByte[28] = 31;
            paramArrayOfByte[17] = 17;
            paramArrayOfByte[18] = 16;
            paramArrayOfByte[19] = 5;
            paramArrayOfByte[15] = 32;
            paramArrayOfByte[29] = 8;
            paramArrayOfByte[30] = 9;
            paramArrayOfByte[31] = 10;
            paramArrayOfByte[21] = 14;
            paramArrayOfByte[4] = 26;
            paramArrayOfByte[22] = 13;
            paramArrayOfByte[23] = 12;
            paramArrayOfByte[1] = 29;
            paramArrayOfByte[2] = 28;
            paramArrayOfByte[20] = 15;
            paramArrayOfByte[0] = 1;
            paramArrayOfByte[13] = 3;
            paramArrayOfByte[14] = 19;
            paramArrayOfByte[7] = 24;
            paramArrayOfByte[8] = 23;
            paramArrayOfByte[16] = 18;
            paramArrayOfByte[3] = 2;
            paramArrayOfByte[24] = 11;
            paramArrayOfByte[25] = 6;
            paramArrayOfByte[9] = 22;
            paramArrayOfByte[10] = 4;
            paramArrayOfByte[11] = 20;
            paramArrayOfByte[12] = 21;
            paramArrayOfByte[26] = 30;
            continue;
            paramArrayOfByte[29] = 19;
            paramArrayOfByte[10] = 4;
            paramArrayOfByte[24] = 30;
            paramArrayOfByte[25] = 6;
            paramArrayOfByte[11] = 3;
            paramArrayOfByte[12] = 21;
            paramArrayOfByte[4] = 26;
            paramArrayOfByte[5] = 27;
            paramArrayOfByte[0] = 29;
            paramArrayOfByte[1] = 1;
            paramArrayOfByte[30] = 9;
            paramArrayOfByte[31] = 10;
            paramArrayOfByte[21] = 11;
            paramArrayOfByte[22] = 13;
            paramArrayOfByte[6] = 24;
            paramArrayOfByte[15] = 32;
            paramArrayOfByte[23] = 12;
            paramArrayOfByte[7] = 25;
            paramArrayOfByte[8] = 23;
            paramArrayOfByte[16] = 18;
            paramArrayOfByte[2] = 28;
            paramArrayOfByte[3] = 2;
            paramArrayOfByte[17] = 17;
            paramArrayOfByte[18] = 5;
            paramArrayOfByte[19] = 16;
            paramArrayOfByte[20] = 15;
            paramArrayOfByte[27] = 7;
            paramArrayOfByte[28] = 31;
            paramArrayOfByte[9] = 22;
            paramArrayOfByte[13] = 20;
            paramArrayOfByte[14] = 8;
            paramArrayOfByte[26] = 14;
        }
    }

    private static byte[] toByteArray(int paramInt)
    {
        return new byte[] { (byte)(paramInt >>> 24), (byte)((0xFFFFFF & paramInt) >>> 16), (byte)((0xFFFF & paramInt) >>> 8), (byte)(paramInt & 0xFF) };
    }

    private static byte[] toByteArray(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
            throws IOException
    {
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(32);
        localByteArrayOutputStream.write(toByteArray(paramInt1));
        localByteArrayOutputStream.write(toByteArray(paramInt2));
        localByteArrayOutputStream.write(toByteArray(paramInt3));
        localByteArrayOutputStream.write(toByteArray(paramInt4));
        localByteArrayOutputStream.write(toByteArray(paramInt5));
        localByteArrayOutputStream.write(toByteArray(paramInt6));
        localByteArrayOutputStream.write(toByteArray(paramInt7));
        localByteArrayOutputStream.write(toByteArray(paramInt8));
        return localByteArrayOutputStream.toByteArray();
    }

    private static int toInteger(byte[] paramArrayOfByte, int paramInt)
    {
        StringBuilder localStringBuilder = new StringBuilder("");
        int i = 0;
        while (i < 4)
        {
            localStringBuilder.append(chars[((byte)((paramArrayOfByte[(paramInt * 4 + i)] & 0xF0) >> 4))]);
            localStringBuilder.append(chars[((byte)(paramArrayOfByte[(paramInt * 4 + i)] & 0xF))]);
            i += 1;
        }
        return Long.valueOf(localStringBuilder.toString(), 16).intValue();
    }
}
