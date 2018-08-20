package com.olym.mjt.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA
{
    private static final String b64pad = "=";
    private static final int chrsz = 8;
    private static final boolean hexcase = false;

    public static String b64_hmac_sha1(String paramString1, String paramString2)
    {
        return binb2b64(core_hmac_sha1(paramString1, paramString2));
    }

    public static String b64_sha1(String paramString)
    {
        String str = paramString;
        if (paramString == null) {
            str = "";
        }
        return binb2b64(core_sha1(str2binb(str), str.length() * 8));
    }

    private static String binb2b64(int[] paramArrayOfInt)
    {
        String str = "";
        int[] arrayOfInt = strechBinArray(paramArrayOfInt, paramArrayOfInt.length * 4);
        int i = 0;
        paramArrayOfInt = str;
        while (i < arrayOfInt.length * 4)
        {
            int k = arrayOfInt[(i >> 2)];
            int m = arrayOfInt[(i + 1 >> 2)];
            int n = arrayOfInt[(i + 2 >> 2)];
            int j = 0;
            if (j < 4)
            {
                if (i * 8 + j * 6 > arrayOfInt.length * 32) {}
                for (paramArrayOfInt = paramArrayOfInt + "=";; paramArrayOfInt = paramArrayOfInt + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(((k >> (3 - i % 4) * 8 & 0xFF) << 16 | (m >> (3 - (i + 1) % 4) * 8 & 0xFF) << 8 | n >> (3 - (i + 2) % 4) * 8 & 0xFF) >> (3 - j) * 6 & 0x3F))
                {
                    j += 1;
                    break;
                }
            }
            i += 3;
        }
        return cleanB64Str(paramArrayOfInt);
    }

    private static String binb2hex(int[] paramArrayOfInt)
    {
        String str = "";
        int i = 0;
        while (i < paramArrayOfInt.length * 4)
        {
            char c1 = "0123456789abcdef".charAt(paramArrayOfInt[(i >> 2)] >> (3 - i % 4) * 8 + 4 & 0xF);
            char c2 = "0123456789abcdef".charAt(paramArrayOfInt[(i >> 2)] >> (3 - i % 4) * 8 & 0xF);
            str = str + new Character(c1).toString() + new Character(c2).toString();
            i += 1;
        }
        return str;
    }

    private static String binb2str(int[] paramArrayOfInt)
    {
        String str = "";
        int i = 0;
        while (i < paramArrayOfInt.length * 32)
        {
            str = str + (char)(paramArrayOfInt[(i >> 5)] >>> 24 - i % 32 & 0xFF);
            i += 8;
        }
        return str;
    }

    private static int bit_rol(int paramInt1, int paramInt2)
    {
        return paramInt1 << paramInt2 | paramInt1 >>> 32 - paramInt2;
    }

    private static String cleanB64Str(String paramString)
    {
        String str = paramString;
        if (paramString == null) {
            str = "";
        }
        int i = str.length();
        if (i <= 1) {
            return str;
        }
        int j = str.charAt(i - 1);
        paramString = "";
        i -= 1;
        while ((i >= 0) && (str.charAt(i) == j))
        {
            paramString = paramString + str.charAt(i);
            i -= 1;
        }
        return str.substring(0, str.indexOf(paramString));
    }

    private static int[] complete216(int[] paramArrayOfInt)
    {
        if (paramArrayOfInt.length >= 16) {
            return paramArrayOfInt;
        }
        int[] arrayOfInt = new int[16 - paramArrayOfInt.length];
        int i = 0;
        while (i < arrayOfInt.length)
        {
            arrayOfInt[i] = 0;
            i += 1;
        }
        return concat(paramArrayOfInt, arrayOfInt);
    }

    private static int[] concat(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
    {
        int[] arrayOfInt = new int[paramArrayOfInt1.length + paramArrayOfInt2.length];
        int i = 0;
        if (i < paramArrayOfInt1.length + paramArrayOfInt2.length)
        {
            if (i < paramArrayOfInt1.length) {
                arrayOfInt[i] = paramArrayOfInt1[i];
            }
            for (;;)
            {
                i += 1;
                break;
                arrayOfInt[i] = paramArrayOfInt2[(i - paramArrayOfInt1.length)];
            }
        }
        return arrayOfInt;
    }

    private static int[] core_hmac_sha1(String paramString1, String paramString2)
    {
        Object localObject = paramString1;
        if (paramString1 == null) {
            localObject = "";
        }
        paramString1 = paramString2;
        if (paramString2 == null) {
            paramString1 = "";
        }
        int[] arrayOfInt = complete216(str2binb((String)localObject));
        paramString2 = arrayOfInt;
        if (arrayOfInt.length > 16) {
            paramString2 = core_sha1(arrayOfInt, ((String)localObject).length() * 8);
        }
        localObject = new int[16];
        arrayOfInt = new int[16];
        int i = 0;
        while (i < 16)
        {
            localObject[i] = 0;
            arrayOfInt[i] = 0;
            i += 1;
        }
        i = 0;
        while (i < 16)
        {
            paramString2[i] ^= 0x36363636;
            paramString2[i] ^= 0x5C5C5C5C;
            i += 1;
        }
        return core_sha1(concat(arrayOfInt, core_sha1(concat((int[])localObject, str2binb(paramString1)), paramString1.length() * 8 + 512)), 672);
    }

    private static int[] core_sha1(int[] paramArrayOfInt, int paramInt)
    {
        paramArrayOfInt = strechBinArray(paramArrayOfInt, paramInt >> 5);
        int i = paramInt >> 5;
        paramArrayOfInt[i] |= 128 << 24 - paramInt % 32;
        paramArrayOfInt = strechBinArray(paramArrayOfInt, (paramInt + 64 >> 9 << 4) + 15);
        paramArrayOfInt[((paramInt + 64 >> 9 << 4) + 15)] = paramInt;
        int[] arrayOfInt = new int[80];
        int m = 1732584193;
        int k = -271733879;
        int j = -1732584194;
        i = 271733878;
        paramInt = -1009589776;
        int i5 = 0;
        while (i5 < paramArrayOfInt.length)
        {
            int i6 = 0;
            int i4 = paramInt;
            int i3 = i;
            int i2 = j;
            int i1 = k;
            int n = m;
            if (i6 < 80)
            {
                if (i6 < 16) {
                    arrayOfInt[i6] = paramArrayOfInt[(i5 + i6)];
                }
                for (;;)
                {
                    int i7 = safe_add(safe_add(rol(n, 5), sha1_ft(i6, i1, i2, i3)), safe_add(safe_add(i4, arrayOfInt[i6]), sha1_kt(i6)));
                    i4 = i3;
                    i3 = i2;
                    i2 = rol(i1, 30);
                    i1 = n;
                    n = i7;
                    i6 += 1;
                    break;
                    arrayOfInt[i6] = rol(arrayOfInt[(i6 - 3)] ^ arrayOfInt[(i6 - 8)] ^ arrayOfInt[(i6 - 14)] ^ arrayOfInt[(i6 - 16)], 1);
                }
            }
            m = safe_add(n, m);
            k = safe_add(i1, k);
            j = safe_add(i2, j);
            i = safe_add(i3, i);
            paramInt = safe_add(i4, paramInt);
            i5 += 16;
        }
        return new int[] { m, k, j, i, paramInt };
    }

    private static void doTest()
    {
        Applog.systemOut("hex_sha1(" + "data" + ")=" + hex_sha1("data"));
        Applog.systemOut("b64_sha1(" + "data" + ")=" + b64_sha1("data"));
        Applog.systemOut("str_sha1(" + "data" + ")=" + str_sha1("data"));
        Applog.systemOut("hex_hmac_sha1(" + "key" + "," + "data" + ")=" + hex_hmac_sha1("key", "data"));
        Applog.systemOut("b64_hmac_sha1(" + "key" + "," + "data" + ")=" + b64_hmac_sha1("key", "data"));
        Applog.systemOut("str_hmac_sha1(" + "key" + "," + "data" + ")=" + str_hmac_sha1("key", "data"));
    }

    public static String getHashString(String paramString)
    {
        return toHexString(hex_sha1(paramString));
    }

    public static String getSha1(String paramString)
    {
        if ((paramString == null) || (paramString.length() == 0)) {
            return null;
        }
        char[] arrayOfChar = new char[16];
        char[] tmp21_19 = arrayOfChar;
        tmp21_19[0] = 48;
        char[] tmp26_21 = tmp21_19;
        tmp26_21[1] = 49;
        char[] tmp31_26 = tmp26_21;
        tmp31_26[2] = 50;
        char[] tmp36_31 = tmp31_26;
        tmp36_31[3] = 51;
        char[] tmp41_36 = tmp36_31;
        tmp41_36[4] = 52;
        char[] tmp46_41 = tmp41_36;
        tmp46_41[5] = 53;
        char[] tmp51_46 = tmp46_41;
        tmp51_46[6] = 54;
        char[] tmp57_51 = tmp51_46;
        tmp57_51[7] = 55;
        char[] tmp63_57 = tmp57_51;
        tmp63_57[8] = 56;
        char[] tmp69_63 = tmp63_57;
        tmp69_63[9] = 57;
        char[] tmp75_69 = tmp69_63;
        tmp75_69[10] = 97;
        char[] tmp81_75 = tmp75_69;
        tmp81_75[11] = 98;
        char[] tmp87_81 = tmp81_75;
        tmp87_81[12] = 99;
        char[] tmp93_87 = tmp87_81;
        tmp93_87[13] = 100;
        char[] tmp99_93 = tmp93_87;
        tmp99_93[14] = 101;
        char[] tmp105_99 = tmp99_93;
        tmp105_99[15] = 102;
        tmp105_99;
        for (;;)
        {
            Object localObject;
            int k;
            int i;
            int j;
            try
            {
                localObject = MessageDigest.getInstance("SHA1");
                ((MessageDigest)localObject).update(paramString.getBytes("UTF-8"));
                paramString = ((MessageDigest)localObject).digest();
                k = paramString.length;
                localObject = new char[k * 2];
                i = 0;
                j = 0;
            }
            catch (NoSuchAlgorithmException paramString)
            {
                paramString.printStackTrace();
                return "";
            }
            catch (UnsupportedEncodingException paramString)
            {
                paramString.printStackTrace();
                continue;
            }
            paramString = new String((char[])localObject);
            return paramString;
            while (i < k)
            {
                int m = paramString[i];
                int n = j + 1;
                localObject[j] = arrayOfChar[(m >>> 4 & 0xF)];
                j = n + 1;
                localObject[n] = arrayOfChar[(m & 0xF)];
                i += 1;
            }
        }
    }

    public static String hex_hmac_sha1(String paramString1, String paramString2)
    {
        return binb2hex(core_hmac_sha1(paramString1, paramString2));
    }

    public static String hex_sha1(String paramString)
    {
        String str = paramString;
        if (paramString == null) {
            str = "";
        }
        return binb2hex(core_sha1(str2binb(str), str.length() * 8));
    }

    private static int rol(int paramInt1, int paramInt2)
    {
        return paramInt1 << paramInt2 | paramInt1 >>> 32 - paramInt2;
    }

    private static int safe_add(int paramInt1, int paramInt2)
    {
        int i = (paramInt1 & 0xFFFF) + (paramInt2 & 0xFFFF);
        return (paramInt1 >> 16) + (paramInt2 >> 16) + (i >> 16) << 16 | i & 0xFFFF;
    }

    private static int sha1_ft(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
        if (paramInt1 < 20) {
            return paramInt2 & paramInt3 | (paramInt2 ^ 0xFFFFFFFF) & paramInt4;
        }
        if (paramInt1 < 40) {
            return paramInt2 ^ paramInt3 ^ paramInt4;
        }
        if (paramInt1 < 60) {
            return paramInt2 & paramInt3 | paramInt2 & paramInt4 | paramInt3 & paramInt4;
        }
        return paramInt2 ^ paramInt3 ^ paramInt4;
    }

    private static int sha1_kt(int paramInt)
    {
        if (paramInt < 20) {
            return 1518500249;
        }
        if (paramInt < 40) {
            return 1859775393;
        }
        if (paramInt < 60) {
            return -1894007588;
        }
        return -899497514;
    }

    private static boolean sha1_vm_test()
    {
        return hex_sha1("abc").equals("a9993e364706816aba3e25717850c26c9cd0d89d");
    }

    private static int[] str2binb(String paramString)
    {
        Object localObject = paramString;
        if (paramString == null) {
            localObject = "";
        }
        paramString = new int[((String)localObject).length() * 8];
        int i = 0;
        while (i < ((String)localObject).length() * 8)
        {
            j = i >> 5;
            paramString[j] |= (((String)localObject).charAt(i / 8) & 0xFF) << 24 - i % 32;
            i += 8;
        }
        i = 0;
        int j = 0;
        while ((j < paramString.length) && (paramString[j] != 0))
        {
            j += 1;
            i += 1;
        }
        localObject = new int[i];
        j = 0;
        while (j < i)
        {
            localObject[j] = paramString[j];
            j += 1;
        }
        return (int[])localObject;
    }

    public static String str_hmac_sha1(String paramString1, String paramString2)
    {
        return binb2str(core_hmac_sha1(paramString1, paramString2));
    }

    public static String str_sha1(String paramString)
    {
        String str = paramString;
        if (paramString == null) {
            str = "";
        }
        return binb2str(core_sha1(str2binb(str), str.length() * 8));
    }

    private static int[] strechBinArray(int[] paramArrayOfInt, int paramInt)
    {
        int j = paramArrayOfInt.length;
        if (j >= paramInt + 1) {
            return paramArrayOfInt;
        }
        int[] arrayOfInt = new int[paramInt + 1];
        int i = 0;
        while (i < paramInt)
        {
            arrayOfInt[i] = 0;
            i += 1;
        }
        paramInt = 0;
        while (paramInt < j)
        {
            arrayOfInt[paramInt] = paramArrayOfInt[paramInt];
            paramInt += 1;
        }
        return arrayOfInt;
    }

    public static String toHexString(String paramString)
    {
        String str1 = "";
        int i = 0;
        while (i < paramString.length())
        {
            String str2 = Integer.toHexString(paramString.charAt(i));
            str1 = str1 + str2;
            i += 1;
        }
        return str1;
    }
}
