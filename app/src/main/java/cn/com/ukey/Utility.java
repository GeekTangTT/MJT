package cn.com.ukey;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.util.ByteArrayBuffer;

public class Utility
{
    public static final int CHAR_SET_GBK = 17;
    public static final int CHAR_SET_UTF8 = 32;
    public static final int LANG_de_DE = 137;
    public static final int LANG_en_US = 128;
    public static final int LANG_fr_FR = 133;
    public static final int LANG_id_ID = 135;
    public static final int LANG_ja_JP = 131;
    public static final int LANG_kk_KZ = 139;
    public static final int LANG_ko_KR = 132;
    public static final int LANG_ru_RU = 134;
    public static final int LANG_sr_SA = 138;
    public static final int LANG_zh_CN = 129;
    public static final int LANG_zh_TW = 130;
    private static final Map<String, Integer> languageCharsetMap = new HashMap();
    private static long lastClickTime;
    private static long lastFastClickTime;
    private static int totolFastClickCount = 0;

    static
    {
        languageCharsetMap.put("GBK", Integer.valueOf(17));
        languageCharsetMap.put("GB2312", Integer.valueOf(17));
        languageCharsetMap.put("UTF-8", Integer.valueOf(32));
        languageCharsetMap.put("en_US", Integer.valueOf(128));
        languageCharsetMap.put("zh_CN", Integer.valueOf(129));
        languageCharsetMap.put("zh_TW", Integer.valueOf(130));
        languageCharsetMap.put("ja_JP", Integer.valueOf(131));
        languageCharsetMap.put("ko_KR", Integer.valueOf(132));
        languageCharsetMap.put("fr_FR", Integer.valueOf(133));
        languageCharsetMap.put("ru_RU", Integer.valueOf(134));
        languageCharsetMap.put("id_ID", Integer.valueOf(135));
        languageCharsetMap.put("de_DE", Integer.valueOf(137));
        languageCharsetMap.put("sr_SA", Integer.valueOf(138));
        languageCharsetMap.put("kk_KZ", Integer.valueOf(139));
        languageCharsetMap.put("th_TH", Integer.valueOf(128));
        languageCharsetMap.put("pl_PL", Integer.valueOf(128));
    }

    public static byte[] DigestCalc(String paramString1, String paramString2)
    {
        byte[] arrayOfByte = paramString1.getBytes();
        if (paramString2 != null) {}
        for (paramString1 = paramString2;; paramString1 = "MD5") {
            try
            {
                if (!paramString2.equals(""))
                {
                    paramString1 = MessageDigest.getInstance(paramString1);
                    paramString1.update(arrayOfByte);
                    paramString1 = paramString1.digest();
                    return paramString1;
                }
            }
            catch (NoSuchAlgorithmException paramString1)
            {
                paramString1.printStackTrace();
                return null;
            }
        }
    }

    public static byte[] DigestCalc(byte[] paramArrayOfByte, String paramString)
    {
        if (paramString != null) {}
        for (String str = paramString;; str = "MD5") {
            try
            {
                if (!paramString.equals(""))
                {
                    paramString = MessageDigest.getInstance(str);
                    paramString.update(paramArrayOfByte);
                    paramArrayOfByte = paramString.digest();
                    return paramArrayOfByte;
                }
            }
            catch (NoSuchAlgorithmException paramArrayOfByte)
            {
                paramArrayOfByte.printStackTrace();
                return null;
            }
        }
    }

    public static byte[] byteReverse(byte[] paramArrayOfByte)
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

    public static String byteToString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
        int i = 0;
        int j = paramInt1;
        for (;;)
        {
            if (j >= paramInt1 + paramInt2) {}
            for (;;)
            {
                byte[] arrayOfByte = new byte[i];
                System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, i);
                return new String(arrayOfByte);
                if (paramArrayOfByte[j] != 0) {
                    break;
                }
                i = j - paramInt1;
            }
            if (j == paramArrayOfByte.length - 1) {
                i = paramArrayOfByte.length - paramInt1;
            }
            j += 1;
        }
    }

    public static final String bytesToHexString(byte[] paramArrayOfByte)
    {
        return bytesToHexString(paramArrayOfByte, paramArrayOfByte.length);
    }

    public static final String bytesToHexString(byte[] paramArrayOfByte, int paramInt)
    {
        if ((paramArrayOfByte == null) || (paramArrayOfByte.length < paramInt) || (paramInt == 0)) {
            return null;
        }
        StringBuilder localStringBuilder = new StringBuilder(paramInt * 2);
        int i = 0;
        for (;;)
        {
            if (i >= paramInt) {
                return localStringBuilder.toString().toUpperCase();
            }
            String str2 = Integer.toHexString(paramArrayOfByte[i] & 0xFF);
            String str1 = str2;
            if (str2.length() == 1) {
                str1 = "0" + str2;
            }
            localStringBuilder.append(str1);
            i += 1;
        }
    }

    public static byte[] decrypt(String paramString, boolean paramBoolean, byte[] paramArrayOfByte)
    {
        paramString = paramString.getBytes();
        try
        {
            paramString = Base64.decode(paramString);
            if (!paramBoolean) {
                return paramString;
            }
            byte[] arrayOfByte = new byte[8];
            int i = 0;
            for (;;)
            {
                if (i >= 8) {
                    return Des3.des3DecodeCBC(paramArrayOfByte, arrayOfByte, paramString);
                }
                arrayOfByte[i] = 0;
                i += 1;
            }
            return null;
        }
        catch (Exception paramString)
        {
            paramString.printStackTrace();
        }
    }

    public static String encrypt(byte[] paramArrayOfByte1, boolean paramBoolean, byte[] paramArrayOfByte2)
    {
        if (paramBoolean) {}
        for (;;)
        {
            try
            {
                paramArrayOfByte1 = Des3.des3EncodeCBC(paramArrayOfByte2, new byte[8], paramArrayOfByte1);
                return Base64.encodeBytes(paramArrayOfByte1);
            }
            catch (Exception paramArrayOfByte1)
            {
                paramArrayOfByte1.printStackTrace();
            }
            paramArrayOfByte1 = (byte[])paramArrayOfByte1.clone();
        }
        return null;
    }

    public static int getCharsetID(String paramString)
    {
        if (!languageCharsetMap.containsKey(paramString)) {
            return 0;
        }
        return ((Integer)languageCharsetMap.get(paramString)).intValue();
    }

    public static byte[] getDecryptKey(String paramString1, String paramString2, String paramString3)
    {
        paramString1 = DigestCalc(paramString1 + paramString2 + paramString3, "SHA-1");
        paramString2 = new byte[24];
        int i = 0;
        int j;
        if (i >= 16)
        {
            j = 16;
            i = 0;
        }
        for (;;)
        {
            if (j >= 24)
            {
                return paramString2;
                paramString2[i] = paramString1[i];
                i += 1;
                break;
            }
            paramString2[j] = paramString1[i];
            j += 1;
            i += 1;
        }
    }

    public static Bitmap getImageFromAssetsFile(Context paramContext, String paramString)
    {
        Object localObject1 = null;
        Object localObject2 = paramContext.getResources().getAssets();
        paramContext = (Context)localObject1;
        try
        {
            localObject2 = ((AssetManager)localObject2).open(paramString);
            paramContext = (Context)localObject1;
            paramString = BitmapFactory.decodeStream((InputStream)localObject2);
            paramContext = paramString;
            ((InputStream)localObject2).close();
            return paramString;
        }
        catch (IOException paramString)
        {
            paramString.printStackTrace();
        }
        return paramContext;
    }

    public static int getLanguageID(String paramString)
    {
        if (!languageCharsetMap.containsKey(paramString)) {
            return 0;
        }
        return ((Integer)languageCharsetMap.get(paramString)).intValue();
    }

    public static int getUnsignedByte(byte paramByte)
    {
        return paramByte & 0xFF;
    }

    public static byte[] hexToBytes(String paramString)
    {
        paramString = paramString.toCharArray();
        int m = paramString.length / 2;
        byte[] arrayOfByte = new byte[m];
        int i = 0;
        for (;;)
        {
            if (i >= m) {
                return arrayOfByte;
            }
            int k = Character.digit(paramString[(i * 2)], 16) << 4 | Character.digit(paramString[(i * 2 + 1)], 16);
            int j = k;
            if (k > 127) {
                j = k - 256;
            }
            arrayOfByte[i] = ((byte)j);
            i += 1;
        }
    }

    public static boolean isFastDoubleClick()
    {
        long l1 = System.currentTimeMillis();
        long l2 = l1 - lastClickTime;
        Log.e("������������", "timeD = " + l2);
        if ((0L < l2) && (l2 < 2000L)) {
            return true;
        }
        lastClickTime = l1;
        return false;
    }

    public static boolean isFastFiveClick()
    {
        long l1 = System.currentTimeMillis();
        long l2 = l1 - lastFastClickTime;
        lastFastClickTime = l1;
        if ((0L < l2) && (l2 < 500L))
        {
            if (totolFastClickCount >= 5)
            {
                totolFastClickCount = 0;
                return true;
            }
            totolFastClickCount += 1;
            return false;
        }
        totolFastClickCount = 0;
        return false;
    }

    public static byte[] read(Context paramContext, String paramString)
    {
        try
        {
            paramContext = paramContext.openFileInput(paramString);
            int i = paramContext.available();
            if (i == 0) {
                return null;
            }
            paramString = new byte[i];
            paramContext.read(paramString);
            paramContext.close();
            return paramString;
        }
        catch (FileNotFoundException paramContext)
        {
            paramContext.printStackTrace();
            return null;
        }
        catch (IOException paramContext)
        {
            for (;;)
            {
                paramContext.printStackTrace();
            }
        }
    }

    public static void sendBroadcastForTest(Context paramContext, String paramString)
    {
        Intent localIntent = new Intent("cn.com.ukey.ukeydeviceioservice.broadcast");
        localIntent.putExtra("message", paramString);
        MyLog.d("CMD_LOG", paramString);
        if (paramContext != null) {
            paramContext.sendBroadcast(localIntent);
        }
    }

    public static void write(Context paramContext, String paramString, byte[] paramArrayOfByte)
    {
        try
        {
            paramContext = paramContext.openFileOutput(paramString, 2);
            paramContext.write(paramArrayOfByte);
            paramContext.flush();
            paramContext.close();
            return;
        }
        catch (FileNotFoundException paramContext)
        {
            paramContext.printStackTrace();
            return;
        }
        catch (IOException paramContext)
        {
            paramContext.printStackTrace();
        }
    }

    public static void writeSMLogToDB(Context paramContext, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4) {}
}
