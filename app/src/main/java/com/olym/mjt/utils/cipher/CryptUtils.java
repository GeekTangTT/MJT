package com.olym.mjt.utils.cipher;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.lc.methodex.LogFinalUtils;
import com.nisc.SecurityEngine;
import com.nisc.SecurityEngineException;
import com.olym.mjt.init.EngineUtils;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.Base64;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CryptUtils
{
    private static final String KEY_MAC = "HmacSHA256";
    public static String RandomPass = null;
    private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    public static String byteArrayToHexString(byte[] paramArrayOfByte)
    {
        StringBuffer localStringBuffer = new StringBuffer();
        int i = 0;
        while (i < paramArrayOfByte.length)
        {
            localStringBuffer.append(byteToHexString(paramArrayOfByte[i]));
            i += 1;
        }
        return localStringBuffer.toString();
    }

    private static String byteToHexString(byte paramByte)
    {
        byte b = paramByte;
        paramByte = b;
        if (b < 0) {
            paramByte = b + 256;
        }
        b = paramByte / 16;
        return hexDigits[b] + hexDigits[(paramByte % 16)];
    }

    public static String changePass(Context paramContext, String paramString1, String paramString2)
    {
        Object localObject = paramString2.replace(",", "");
        paramString2 = (String)localObject;
        if (TextUtils.isEmpty((CharSequence)localObject)) {
            paramString2 = "";
        }
        paramContext = deviceIdMaterial(paramContext);
        Applog.info_importance("changePass didMaterial:" + paramContext);
        paramContext = InternalHash.InternalHash(paramContext.getBytes(), 1);
        byte[] arrayOfByte = InternalHash.InternalHash(paramContext, 3);
        localObject = InternalHash.InternalHash(paramContext, 4);
        paramContext = getStringRandom(32);
        if (RandomPass != null) {
            paramContext = RandomPass;
        }
        paramString2 = toString(InternalHash.InternalHash((paramString2 + paramContext + toString(arrayOfByte)).getBytes(), 5)).substring(0, 64);
        arrayOfByte = new byte[64];
        new InternalCipher().InternalEncrypt(paramContext.getBytes(), 32, (byte[])localObject, arrayOfByte);
        paramContext = toString(arrayOfByte);
        try
        {
            EngineUtils.getInstance().changePassword(paramString1, paramString2);
            return paramContext;
        }
        catch (SecurityEngineException paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return paramContext;
    }

    public static byte[] decryptFileRandomPass(String paramString1, String paramString2)
    {
        Applog.info_importance("decryptFileRandomPass phone:" + paramString1 + "============" + paramString2);
        try
        {
            SecurityEngine localSecurityEngine = SecurityEngine.getInstance();
            paramString2 = Base64.decode(paramString2);
            int i = paramString2.length;
            int[] arrayOfInt = new int[1];
            arrayOfInt[0] = 0;
            localSecurityEngine.CryptImportDataEx(paramString1, paramString2, i, null, arrayOfInt);
            byte[] arrayOfByte = new byte[arrayOfInt[0]];
            localSecurityEngine.CryptImportDataEx(paramString1, paramString2, i, arrayOfByte, arrayOfInt);
            return arrayOfByte;
        }
        catch (SecurityEngineException paramString1)
        {
            Applog.info_importance("decryptFileRandomPass SecurityEngineException:" + paramString1.getMessage());
            LogFinalUtils.logForException(paramString1);
            return null;
        }
        catch (UnsupportedEncodingException paramString1)
        {
            for (;;)
            {
                Applog.info_importance("decryptFileRandomPass UnsupportedEncodingException:" + paramString1.getMessage());
                LogFinalUtils.logForException(paramString1);
            }
        }
    }

    public static String decryptPass(Context paramContext, String paramString1, String paramString2)
    {
        if (TextUtils.isEmpty(paramString1)) {
            return "";
        }
        Object localObject = paramString2;
        if (TextUtils.isEmpty(paramString2)) {
            localObject = "";
        }
        paramString2 = ((String)localObject).replace(",", "");
        paramContext = deviceIdMaterial(paramContext);
        Applog.info_importance("decryptPass didMaterial:" + paramContext);
        localObject = InternalHash.InternalHash(paramContext.getBytes(), 1);
        paramContext = InternalHash.InternalHash((byte[])localObject, 3);
        localObject = InternalHash.InternalHash((byte[])localObject, 4);
        byte[] arrayOfByte = new byte[32];
        paramString1 = toByte(paramString1);
        int i = new InternalCipher().InternalDecrypt(paramString1, paramString1.length, (byte[])localObject, arrayOfByte);
        if (i == -1) {
            return "";
        }
        return toString(InternalHash.InternalHash((paramString2 + new String(arrayOfByte).trim() + toString(paramContext)).getBytes(), 5), i).substring(0, 64);
    }

    public static String decryptRandomPass(String paramString1, String paramString2)
    {
        Object localObject = EngineUtils.getInstance().decryptCms(paramString1, paramString2);
        if (localObject == null) {
            return "";
        }
        paramString1 = getRandomKey().getBytes();
        paramString2 = new byte[32];
        localObject = toByte((String)localObject);
        new InternalCipher().InternalDecrypt((byte[])localObject, localObject.length, paramString1, paramString2);
        return new String(paramString2);
    }

    public static String deviceIdMaterial(Context paramContext)
    {
        Object localObject2 = UserSpUtil.getInstanse().getDisplayMetrics();
        Object localObject1 = localObject2;
        if (TextUtils.isEmpty((CharSequence)localObject2))
        {
            localObject1 = paramContext.getResources().getDisplayMetrics();
            int i = ((DisplayMetrics)localObject1).heightPixels;
            int j = ((DisplayMetrics)localObject1).widthPixels;
            localObject1 = String.valueOf(i) + String.valueOf(j);
            UserSpUtil.getInstanse().setDisplayMetrics((String)localObject1);
        }
        Object localObject3 = UserSpUtil.getInstanse().getAndroidId();
        localObject2 = localObject3;
        if (TextUtils.isEmpty((CharSequence)localObject3))
        {
            localObject2 = Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
            UserSpUtil.getInstanse().setAndroidId((String)localObject2);
        }
        Object localObject4 = UserSpUtil.getInstanse().getMacAddress();
        if (localObject4 != null)
        {
            localObject3 = localObject4;
            if (!TextUtils.isEmpty((CharSequence)localObject4)) {}
        }
        else
        {
            localObject3 = getMacAddress(paramContext);
            UserSpUtil.getInstanse().setMacAddress((String)localObject3);
        }
        localObject4 = UserSpUtil.getInstanse().getBluetooth();
        if (localObject4 != null)
        {
            paramContext = (Context)localObject4;
            if (!TextUtils.isEmpty((CharSequence)localObject4)) {}
        }
        else
        {
            paramContext = BluetoothAdapter.getDefaultAdapter().getAddress();
            UserSpUtil.getInstanse().setBluetooth(paramContext);
        }
        String str2 = Build.BOARD;
        String str3 = Build.BRAND;
        String str4 = Build.DEVICE;
        String str1 = UserSpUtil.getInstanse().getKeyDisplayversion();
        localObject4 = str1;
        if (TextUtils.isEmpty(str1))
        {
            localObject4 = Build.DISPLAY;
            UserSpUtil.getInstanse().setKeyDisplayversion((String)localObject4);
        }
        str1 = Build.HARDWARE;
        String str5 = Build.MANUFACTURER;
        String str6 = Build.SERIAL;
        String str7 = (String)localObject1 + (String)localObject2 + (String)localObject3 + paramContext + str2 + str3 + str4 + (String)localObject4 + str1 + str5 + str6;
        Applog.info("-------devId---- " + str7);
        Applog.info("displaymetrics==" + (String)localObject1 + "==androidId==" + (String)localObject2 + "==macAddress==" + (String)localObject3 + "==blueTooth==" + paramContext + "==board==" + str2 + "\n==brand==" + str3 + "==device==" + str4 + "==systemversionnumber==" + (String)localObject4 + "==hardware==" + str1 + "==manufacturer==" + str5 + "==serial==" + str6);
        return str7;
    }

    public static String encryptFileRandomPass(String paramString, byte[] paramArrayOfByte)
    {
        try
        {
            SecurityEngine localSecurityEngine = SecurityEngine.getInstance();
            int i = paramArrayOfByte.length;
            int[] arrayOfInt = new int[1];
            arrayOfInt[0] = 0;
            localSecurityEngine.CryptExportDataEx(paramString, paramString, paramArrayOfByte, i, null, arrayOfInt);
            byte[] arrayOfByte = new byte[arrayOfInt[0]];
            localSecurityEngine.CryptExportDataEx(paramString, paramString, paramArrayOfByte, i, arrayOfByte, arrayOfInt);
            paramString = Base64.encode(arrayOfByte);
            return paramString;
        }
        catch (Exception paramString)
        {
            paramString.printStackTrace();
        }
        return "";
    }

    public static String encryptHMAC(String paramString1, String paramString2)
    {
        if (paramString1 == null) {
            return null;
        }
        return byteArrayToHexString(encryptHMAC(paramString1.getBytes(), paramString2));
    }

    public static byte[] encryptHMAC(byte[] paramArrayOfByte, String paramString)
    {
        try
        {
            paramString = new SecretKeySpec(paramString.getBytes(), "HmacSHA256");
            Mac localMac = Mac.getInstance(paramString.getAlgorithm());
            localMac.init(paramString);
            paramArrayOfByte = localMac.doFinal(paramArrayOfByte);
            return paramArrayOfByte;
        }
        catch (Exception paramArrayOfByte)
        {
            paramArrayOfByte.printStackTrace();
            LogFinalUtils.logForException(paramArrayOfByte);
        }
        return null;
    }

    public static String encryptRandomPass(String paramString1, String paramString2)
    {
        byte[] arrayOfByte1 = getRandomKey().getBytes();
        byte[] arrayOfByte2 = new byte[64];
        new InternalCipher().InternalEncrypt(paramString2.getBytes(), paramString2.length(), arrayOfByte1, arrayOfByte2);
        paramString2 = toString(arrayOfByte2);
        try
        {
            paramString1 = EngineUtils.getInstance().cryptCms(paramString1, paramString1, paramString2);
            return paramString1;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
        }
        return "";
    }

    public static String getDeviceId(Context paramContext)
    {
        paramContext = InternalHash.InternalHash(InternalHash.InternalHash(deviceIdMaterial(paramContext).getBytes(), 1), 2);
        return toString(paramContext, paramContext.length);
    }

    public static String getDeviceIdDigest(Context paramContext)
    {
        paramContext = getDeviceId(paramContext).getBytes();
        try
        {
            MessageDigest localMessageDigest = MessageDigest.getInstance("SHA-256");
            localMessageDigest.update(paramContext);
            paramContext = toString(localMessageDigest.digest(), localMessageDigest.digest().length);
            return paramContext;
        }
        catch (NoSuchAlgorithmException paramContext)
        {
            LogFinalUtils.logForException(paramContext);
        }
        return null;
    }

    public static String getMacAddress(Context paramContext)
    {
        String str = "";
        paramContext = (WifiManager)paramContext.getSystemService("wifi");
        if (paramContext == null) {}
        for (paramContext = null;; paramContext = paramContext.getConnectionInfo())
        {
            if (paramContext != null)
            {
                if (TextUtils.isEmpty(paramContext.getMacAddress())) {
                    break;
                }
                str = paramContext.getMacAddress().replace(":", "");
            }
            return str;
        }
        return "";
    }

    private static String getRandomKey()
    {
        char[] arrayOfChar1 = new char[64];
        char[] tmp6_5 = arrayOfChar1;
        tmp6_5[0] = 48;
        char[] tmp12_6 = tmp6_5;
        tmp12_6[1] = 49;
        char[] tmp18_12 = tmp12_6;
        tmp18_12[2] = 50;
        char[] tmp24_18 = tmp18_12;
        tmp24_18[3] = 51;
        char[] tmp30_24 = tmp24_18;
        tmp30_24[4] = 52;
        char[] tmp36_30 = tmp30_24;
        tmp36_30[5] = 53;
        char[] tmp42_36 = tmp36_30;
        tmp42_36[6] = 54;
        char[] tmp49_42 = tmp42_36;
        tmp49_42[7] = 55;
        char[] tmp56_49 = tmp49_42;
        tmp56_49[8] = 56;
        char[] tmp63_56 = tmp56_49;
        tmp63_56[9] = 57;
        char[] tmp70_63 = tmp63_56;
        tmp70_63[10] = 65;
        char[] tmp77_70 = tmp70_63;
        tmp77_70[11] = 66;
        char[] tmp84_77 = tmp77_70;
        tmp84_77[12] = 67;
        char[] tmp91_84 = tmp84_77;
        tmp91_84[13] = 68;
        char[] tmp98_91 = tmp91_84;
        tmp98_91[14] = 69;
        char[] tmp105_98 = tmp98_91;
        tmp105_98[15] = 70;
        char[] tmp112_105 = tmp105_98;
        tmp112_105[16] = 71;
        char[] tmp119_112 = tmp112_105;
        tmp119_112[17] = 72;
        char[] tmp126_119 = tmp119_112;
        tmp126_119[18] = 73;
        char[] tmp133_126 = tmp126_119;
        tmp133_126[19] = 74;
        char[] tmp140_133 = tmp133_126;
        tmp140_133[20] = 75;
        char[] tmp147_140 = tmp140_133;
        tmp147_140[21] = 76;
        char[] tmp154_147 = tmp147_140;
        tmp154_147[22] = 77;
        char[] tmp161_154 = tmp154_147;
        tmp161_154[23] = 78;
        char[] tmp168_161 = tmp161_154;
        tmp168_161[24] = 79;
        char[] tmp175_168 = tmp168_161;
        tmp175_168[25] = 80;
        char[] tmp182_175 = tmp175_168;
        tmp182_175[26] = 81;
        char[] tmp189_182 = tmp182_175;
        tmp189_182[27] = 82;
        char[] tmp196_189 = tmp189_182;
        tmp196_189[28] = 83;
        char[] tmp203_196 = tmp196_189;
        tmp203_196[29] = 84;
        char[] tmp210_203 = tmp203_196;
        tmp210_203[30] = 85;
        char[] tmp217_210 = tmp210_203;
        tmp217_210[31] = 86;
        char[] tmp224_217 = tmp217_210;
        tmp224_217[32] = 87;
        char[] tmp231_224 = tmp224_217;
        tmp231_224[33] = 88;
        char[] tmp238_231 = tmp231_224;
        tmp238_231[34] = 89;
        char[] tmp245_238 = tmp238_231;
        tmp245_238[35] = 90;
        char[] tmp252_245 = tmp245_238;
        tmp252_245[36] = 97;
        char[] tmp259_252 = tmp252_245;
        tmp259_252[37] = 98;
        char[] tmp266_259 = tmp259_252;
        tmp266_259[38] = 99;
        char[] tmp273_266 = tmp266_259;
        tmp273_266[39] = 100;
        char[] tmp280_273 = tmp273_266;
        tmp280_273[40] = 101;
        char[] tmp287_280 = tmp280_273;
        tmp287_280[41] = 102;
        char[] tmp294_287 = tmp287_280;
        tmp294_287[42] = 103;
        char[] tmp301_294 = tmp294_287;
        tmp301_294[43] = 104;
        char[] tmp308_301 = tmp301_294;
        tmp308_301[44] = 105;
        char[] tmp315_308 = tmp308_301;
        tmp315_308[45] = 106;
        char[] tmp322_315 = tmp315_308;
        tmp322_315[46] = 107;
        char[] tmp329_322 = tmp322_315;
        tmp329_322[47] = 108;
        char[] tmp336_329 = tmp329_322;
        tmp336_329[48] = 109;
        char[] tmp343_336 = tmp336_329;
        tmp343_336[49] = 110;
        char[] tmp350_343 = tmp343_336;
        tmp350_343[50] = 111;
        char[] tmp357_350 = tmp350_343;
        tmp357_350[51] = 112;
        char[] tmp364_357 = tmp357_350;
        tmp364_357[52] = 113;
        char[] tmp371_364 = tmp364_357;
        tmp371_364[53] = 114;
        char[] tmp378_371 = tmp371_364;
        tmp378_371[54] = 115;
        char[] tmp385_378 = tmp378_371;
        tmp385_378[55] = 116;
        char[] tmp392_385 = tmp385_378;
        tmp392_385[56] = 117;
        char[] tmp399_392 = tmp392_385;
        tmp399_392[57] = 118;
        char[] tmp406_399 = tmp399_392;
        tmp406_399[58] = 119;
        char[] tmp413_406 = tmp406_399;
        tmp413_406[59] = 120;
        char[] tmp420_413 = tmp413_406;
        tmp420_413[60] = 121;
        char[] tmp427_420 = tmp420_413;
        tmp427_420[61] = 122;
        char[] tmp434_427 = tmp427_420;
        tmp434_427[62] = 48;
        char[] tmp441_434 = tmp434_427;
        tmp441_434[63] = 49;
        tmp441_434;
        int k = arrayOfChar1.length / 2;
        char[] arrayOfChar2 = new char[k];
        int j = 0;
        int i = 0;
        while (j < k)
        {
            arrayOfChar2[i] = arrayOfChar1[j];
            j += 1;
            i += 1;
        }
        return new String(arrayOfChar2, 0, k);
    }

    public static String getRootDid(Context paramContext)
    {
        paramContext = InternalHash.InternalHash(deviceIdMaterial(paramContext).getBytes(), 1);
        return toString(paramContext, paramContext.length).substring(0, 64);
    }

    public static String getStringRandom(int paramInt)
    {
        Object localObject2 = "";
        Random localRandom = new Random();
        int i = 0;
        if (i < paramInt)
        {
            String str;
            label36:
            int j;
            label61:
            Object localObject1;
            if (localRandom.nextInt(2) % 2 == 0)
            {
                str = "char";
                if (!"char".equalsIgnoreCase(str)) {
                    break label114;
                }
                if (localRandom.nextInt(2) % 2 != 0) {
                    break label108;
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
                break label36;
                label108:
                j = 97;
                break label61;
                label114:
                localObject1 = localObject2;
                if ("num".equalsIgnoreCase(str)) {
                    localObject1 = (String)localObject2 + String.valueOf(localRandom.nextInt(10));
                }
            }
        }
        return (String)localObject2;
    }

    public static String nativeDecrypt(Context paramContext, String paramString)
            throws UnsupportedEncodingException
    {
        paramString = Base64.decode(paramString);
        Object localObject = deviceIdMaterial(paramContext);
        paramContext = new byte[paramString.length];
        localObject = InternalHash.InternalHash(((String)localObject).getBytes(), 2);
        int i = new InternalCipher().InternalDecrypt(paramString, paramString.length, (byte[])localObject, paramContext);
        if (i <= 0) {
            return null;
        }
        paramString = new byte[i];
        System.arraycopy(paramContext, 0, paramString, 0, i);
        return new String(paramString, "UTF-8");
    }

    public static String nativeEncrypt(Context paramContext, String paramString)
    {
        paramString = paramString.getBytes();
        Object localObject = deviceIdMaterial(paramContext);
        paramContext = new byte[(paramString.length + 16) / 16 * 16 + 16];
        localObject = InternalHash.InternalHash(((String)localObject).getBytes(), 2);
        int i = new InternalCipher().InternalEncrypt(paramString, paramString.length, (byte[])localObject, paramContext);
        if (i <= 0) {
            return null;
        }
        paramString = new byte[i];
        System.arraycopy(paramContext, 0, paramString, 0, i);
        return Base64.encode(paramString);
    }

    public static byte[] toByte(String paramString)
    {
        if (paramString == null) {
            paramString = null;
        }
        for (;;)
        {
            return paramString;
            String str = paramString.trim();
            int i = str.length();
            if ((i == 0) || (i % 2 == 1)) {
                return null;
            }
            byte[] arrayOfByte = new byte[i / 2];
            i = 0;
            paramString = arrayOfByte;
            try
            {
                if (i < str.length())
                {
                    arrayOfByte[(i / 2)] = ((byte)Integer.decode("0x" + str.substring(i, i + 2)).intValue());
                    i += 2;
                }
            }
            catch (Exception paramString)
            {
                LogFinalUtils.logForException(paramString);
            }
        }
        return null;
    }

    public static String toString(byte[] paramArrayOfByte)
    {
        StringBuffer localStringBuffer = new StringBuffer();
        int i = 0;
        if (i < paramArrayOfByte.length)
        {
            String str = Integer.toHexString(paramArrayOfByte[i] & 0xFF);
            if (str.length() == 1) {
                localStringBuffer.append("0" + str);
            }
            for (;;)
            {
                i += 1;
                break;
                localStringBuffer.append(str);
            }
        }
        return localStringBuffer.toString();
    }

    public static String toString(byte[] paramArrayOfByte, int paramInt)
    {
        StringBuffer localStringBuffer = new StringBuffer();
        int i = 0;
        if (i < paramInt)
        {
            String str = Integer.toHexString(paramArrayOfByte[i] & 0xFF);
            if (str.length() == 1) {
                localStringBuffer.append("0" + str);
            }
            for (;;)
            {
                i += 1;
                break;
                localStringBuffer.append(str);
            }
        }
        return localStringBuffer.toString();
    }
}
