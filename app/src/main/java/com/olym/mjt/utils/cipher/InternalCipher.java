package com.olym.mjt.utils.cipher;

import java.util.Arrays;
import java.util.Random;

public class InternalCipher
{
    private static final int BLOCK = 16;
    private static final int DECRYPT = 0;
    private static final int ENCRYPT = 1;
    public static final int ROUND = 32;
    private int[] CK = { 462357, 472066609, 943670861, 1415275113, 1886879365, -1936483679, -1464879427, -993275175, -521670923, -66909679, 404694573, 876298825, 1347903077, 1819507329, -2003855715, -1532251463, -1060647211, -589042959, -117504499, 337322537, 808926789, 1280531041, 1752135293, -2071227751, -1599623499, -1128019247, -656414995, -184876535, 269950501, 741554753, 1213159005, 1684763257 };
    private byte[] Sbox = { -42, -112, -23, -2, -52, -31, 61, -73, 22, -74, 20, -62, 40, -5, 44, 5, 43, 103, -102, 118, 42, -66, 4, -61, -86, 68, 19, 38, 73, -122, 6, -103, -100, 66, 80, -12, -111, -17, -104, 122, 51, 84, 11, 67, -19, -49, -84, 98, -28, -77, 28, -87, -55, 8, -24, -107, -128, -33, -108, -6, 117, -113, 63, -90, 71, 7, -89, -4, -13, 115, 23, -70, -125, 89, 60, 25, -26, -123, 79, -88, 104, 107, -127, -78, 113, 100, -38, -117, -8, -21, 15, 75, 112, 86, -99, 53, 30, 36, 14, 94, 99, 88, -47, -94, 37, 34, 124, 59, 1, 33, 120, -121, -44, 0, 70, 87, -97, -45, 39, 82, 76, 54, 2, -25, -96, -60, -56, -98, -22, -65, -118, -46, 64, -57, 56, -75, -93, -9, -14, -50, -7, 97, 21, -95, -32, -82, 93, -92, -101, 52, 26, 85, -83, -109, 50, 48, -11, -116, -79, -29, 29, -10, -30, 46, -126, 102, -54, 96, -64, 41, 35, -85, 13, 83, 78, 111, -43, -37, 55, 69, -34, -3, -114, 47, 3, -1, 106, 114, 109, 108, 91, 81, -115, 27, -81, -110, -69, -35, -68, 127, 17, -39, 92, 65, 31, 16, 90, -40, 10, -63, 49, -120, -91, -51, 123, -67, 45, 116, -48, 18, -72, -27, -76, -80, -119, 105, -105, 74, 12, -106, 119, 126, 101, -71, -15, 9, -59, 110, -58, -124, 24, -16, 125, -20, 58, -36, 77, 32, 121, -18, 95, 62, -41, -53, 57, 72 };

    private int ByteSub(int paramInt)
    {
        return (this.Sbox[(paramInt >>> 24 & 0xFF)] & 0xFF) << 24 | (this.Sbox[(paramInt >>> 16 & 0xFF)] & 0xFF) << 16 | (this.Sbox[(paramInt >>> 8 & 0xFF)] & 0xFF) << 8 | this.Sbox[(paramInt & 0xFF)] & 0xFF;
    }

    private int InternalCipherOp(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, int paramInt2)
    {
        int i = 0;
        int[] arrayOfInt = new int[32];
        InternalKeyExt(paramArrayOfByte2, arrayOfInt, paramInt2);
        paramArrayOfByte2 = new byte[16];
        byte[] arrayOfByte1 = new byte[16];
        byte[] arrayOfByte2 = new byte[16];
        if (1 == paramInt2)
        {
            arrayOfByte2 = getStringRandom(16).getBytes();
            System.arraycopy(arrayOfByte2, 0, paramArrayOfByte3, 0, 16);
            paramInt2 = paramInt1;
            paramInt1 = i;
            while (paramInt2 >= 16)
            {
                paramArrayOfByte2 = Arrays.copyOfRange(paramArrayOfByte1, paramInt1, paramInt1 + 16);
                i = 0;
                while (i < 16)
                {
                    paramArrayOfByte2[i] = ((byte)(arrayOfByte2[i] ^ paramArrayOfByte2[i]));
                    i += 1;
                }
                InternalCrypt(paramArrayOfByte2, arrayOfByte1, arrayOfInt);
                System.arraycopy(arrayOfByte1, 0, paramArrayOfByte3, paramInt1 + 16, 16);
                System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, 16);
                paramInt2 -= 16;
                paramInt1 += 16;
            }
            i = 0;
            while (i < paramInt2)
            {
                paramArrayOfByte2[i] = paramArrayOfByte1[(paramInt1 + i)];
                i += 1;
            }
            i = 0;
            while (i < 16 - paramInt2)
            {
                paramArrayOfByte2[(paramInt2 + i)] = ((byte)(i + 1));
                i += 1;
            }
            paramInt2 = 0;
            while (paramInt2 < 16)
            {
                paramArrayOfByte2[paramInt2] = ((byte)(arrayOfByte2[paramInt2] ^ paramArrayOfByte2[paramInt2]));
                paramInt2 += 1;
            }
            InternalCrypt(paramArrayOfByte2, arrayOfByte1, arrayOfInt);
            System.arraycopy(arrayOfByte1, 0, paramArrayOfByte3, paramInt1 + 16, 16);
            System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, 16);
            return paramInt1 + 32;
        }
        System.arraycopy(paramArrayOfByte1, 0, arrayOfByte2, 0, 16);
        paramInt2 = paramInt1 - 16;
        i = 16;
        if (paramInt2 >= 16)
        {
            paramArrayOfByte2 = Arrays.copyOfRange(paramArrayOfByte1, i, i + 16);
            InternalCrypt(paramArrayOfByte2, arrayOfByte1, arrayOfInt);
            paramInt1 = 0;
            while (paramInt1 < 16)
            {
                arrayOfByte1[paramInt1] = ((byte)(arrayOfByte2[paramInt1] ^ arrayOfByte1[paramInt1]));
                paramInt1 += 1;
            }
            if (paramInt2 - 16 > 0)
            {
                System.arraycopy(arrayOfByte1, 0, paramArrayOfByte3, i - 16, 16);
                paramInt1 = i + 16;
            }
            for (;;)
            {
                System.arraycopy(paramArrayOfByte2, 0, arrayOfByte2, 0, 16);
                paramInt2 -= 16;
                i = paramInt1;
                break;
                int k = arrayOfByte1[15];
                if ((k > 16) || (k <= 0)) {
                    return -1;
                }
                paramInt1 = 16 - k;
                int j = 1;
                while (paramInt1 < 16)
                {
                    if (arrayOfByte1[paramInt1] != j) {
                        return -1;
                    }
                    j += 1;
                    paramInt1 += 1;
                }
                paramInt1 = i;
                if (k < 16)
                {
                    System.arraycopy(arrayOfByte1, 0, paramArrayOfByte3, i - 16, 16 - k);
                    paramInt1 = i + (16 - k);
                }
            }
        }
        return i - 16;
    }

    private void InternalKeyExt(byte[] paramArrayOfByte, int[] paramArrayOfInt, int paramInt)
    {
        int[] arrayOfInt1 = new int[4];
        int[] arrayOfInt2 = new int[4];
        int i = 0;
        while (i < 4)
        {
            arrayOfInt2[0] = (paramArrayOfByte[(i * 4 + 0)] & 0xFF);
            arrayOfInt2[1] = (paramArrayOfByte[(i * 4 + 1)] & 0xFF);
            arrayOfInt2[2] = (paramArrayOfByte[(i * 4 + 2)] & 0xFF);
            arrayOfInt2[3] = (paramArrayOfByte[(i * 4 + 3)] & 0xFF);
            arrayOfInt1[i] = (arrayOfInt2[0] << 24 | arrayOfInt2[1] << 16 | arrayOfInt2[2] << 8 | arrayOfInt2[3]);
            i += 1;
        }
        arrayOfInt1[0] ^= 0xA3B1BAC6;
        arrayOfInt1[1] ^= 0x56AA3350;
        arrayOfInt1[2] ^= 0x677D9197;
        arrayOfInt1[3] ^= 0xB27022DC;
        i = 0;
        while (i < 32)
        {
            int j = ByteSub(arrayOfInt1[1] ^ arrayOfInt1[2] ^ arrayOfInt1[3] ^ this.CK[(i + 0)]);
            j = arrayOfInt1[0] ^ L2(j);
            arrayOfInt1[0] = j;
            paramArrayOfInt[(i + 0)] = j;
            j = ByteSub(arrayOfInt1[2] ^ arrayOfInt1[3] ^ arrayOfInt1[0] ^ this.CK[(i + 1)]);
            j = arrayOfInt1[1] ^ L2(j);
            arrayOfInt1[1] = j;
            paramArrayOfInt[(i + 1)] = j;
            j = ByteSub(arrayOfInt1[3] ^ arrayOfInt1[0] ^ arrayOfInt1[1] ^ this.CK[(i + 2)]);
            j = arrayOfInt1[2] ^ L2(j);
            arrayOfInt1[2] = j;
            paramArrayOfInt[(i + 2)] = j;
            j = ByteSub(arrayOfInt1[0] ^ arrayOfInt1[1] ^ arrayOfInt1[2] ^ this.CK[(i + 3)]);
            j = arrayOfInt1[3] ^ L2(j);
            arrayOfInt1[3] = j;
            paramArrayOfInt[(i + 3)] = j;
            i += 4;
        }
        if (paramInt == 0)
        {
            paramInt = 0;
            while (paramInt < 16)
            {
                i = paramArrayOfInt[paramInt];
                paramArrayOfInt[paramInt] = paramArrayOfInt[(31 - paramInt)];
                paramArrayOfInt[(31 - paramInt)] = i;
                paramInt += 1;
            }
        }
    }

    private int L1(int paramInt)
    {
        return Rotl(paramInt, 2) ^ paramInt ^ Rotl(paramInt, 10) ^ Rotl(paramInt, 18) ^ Rotl(paramInt, 24);
    }

    private int L2(int paramInt)
    {
        return Rotl(paramInt, 13) ^ paramInt ^ Rotl(paramInt, 23);
    }

    private int Rotl(int paramInt1, int paramInt2)
    {
        return paramInt1 << paramInt2 | paramInt1 >>> 32 - paramInt2;
    }

    private static String getStringRandom(int paramInt)
    {
        Object localObject2 = "";
        Random localRandom = new Random();
        int i = 0;
        if (i < paramInt)
        {
            String str;
            label37:
            int j;
            label62:
            Object localObject1;
            if (localRandom.nextInt(2) % 2 == 0)
            {
                str = "char";
                if (!"char".equalsIgnoreCase(str)) {
                    break label115;
                }
                if (localRandom.nextInt(2) % 2 != 0) {
                    break label109;
                }
                j = 65;
                localObject1 = (String)localObject2 + (char)(localRandom.nextInt(26) + j);
            }
            for (;;)
            {
                i += 1;
                localObject2 = localObject1;
                break;
                str = "num";
                break label37;
                label109:
                j = 97;
                break label62;
                label115:
                localObject1 = localObject2;
                if ("num".equalsIgnoreCase(str)) {
                    localObject1 = (String)localObject2 + String.valueOf(localRandom.nextInt(10));
                }
            }
        }
        return (String)localObject2;
    }

    void InternalCrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int[] paramArrayOfInt)
    {
        int[] arrayOfInt1 = new int[4];
        int[] arrayOfInt2 = new int[4];
        int i = 0;
        while (i < 4)
        {
            arrayOfInt2[0] = (paramArrayOfByte1[(i * 4 + 0)] & 0xFF);
            arrayOfInt2[1] = (paramArrayOfByte1[(i * 4 + 1)] & 0xFF);
            arrayOfInt2[2] = (paramArrayOfByte1[(i * 4 + 2)] & 0xFF);
            arrayOfInt2[3] = (paramArrayOfByte1[(i * 4 + 3)] & 0xFF);
            arrayOfInt1[i] = (arrayOfInt2[0] << 24 | arrayOfInt2[1] << 16 | arrayOfInt2[2] << 8 | arrayOfInt2[3]);
            i += 1;
        }
        i = 0;
        while (i < 32)
        {
            int j = ByteSub(arrayOfInt1[1] ^ arrayOfInt1[2] ^ arrayOfInt1[3] ^ paramArrayOfInt[(i + 0)]);
            arrayOfInt1[0] ^= L1(j);
            j = ByteSub(arrayOfInt1[2] ^ arrayOfInt1[3] ^ arrayOfInt1[0] ^ paramArrayOfInt[(i + 1)]);
            arrayOfInt1[1] ^= L1(j);
            j = ByteSub(arrayOfInt1[3] ^ arrayOfInt1[0] ^ arrayOfInt1[1] ^ paramArrayOfInt[(i + 2)]);
            arrayOfInt1[2] ^= L1(j);
            j = ByteSub(arrayOfInt1[0] ^ arrayOfInt1[1] ^ arrayOfInt1[2] ^ paramArrayOfInt[(i + 3)]);
            arrayOfInt1[3] ^= L1(j);
            i += 4;
        }
        i = 0;
        while (i < 16)
        {
            paramArrayOfByte2[i] = ((byte)(arrayOfInt1[(3 - i / 4)] >>> 24 & 0xFF));
            paramArrayOfByte2[(i + 1)] = ((byte)(arrayOfInt1[(3 - i / 4)] >>> 16 & 0xFF));
            paramArrayOfByte2[(i + 2)] = ((byte)(arrayOfInt1[(3 - i / 4)] >>> 8 & 0xFF));
            paramArrayOfByte2[(i + 3)] = ((byte)(arrayOfInt1[(3 - i / 4)] & 0xFF));
            i += 4;
        }
    }

    public int InternalDecrypt(byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
    {
        return InternalCipherOp(paramArrayOfByte1, paramInt, paramArrayOfByte2, paramArrayOfByte3, 0);
    }

    public int InternalEncrypt(byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
    {
        return InternalCipherOp(paramArrayOfByte1, paramInt, paramArrayOfByte2, paramArrayOfByte3, 1);
    }
}
