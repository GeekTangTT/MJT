package com.olym.mjt.utils;

import com.lc.methodex.LogFinalUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZipUtil
{
    public static final String GZIP_ENCODE_UTF_8 = "UTF-8";

    public static String compress(String paramString)
    {
        try
        {
            paramString = new String(compress(paramString, "UTF-8"), "UTF-8");
            return paramString;
        }
        catch (UnsupportedEncodingException paramString)
        {
            LogFinalUtils.logForException(paramString);
        }
        return null;
    }

    public static byte[] compress(String paramString1, String paramString2)
    {
        if ((paramString1 == null) || (paramString1.length() == 0)) {
            return null;
        }
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        try
        {
            GZIPOutputStream localGZIPOutputStream = new GZIPOutputStream(localByteArrayOutputStream);
            localGZIPOutputStream.write(paramString1.getBytes(paramString2));
            localGZIPOutputStream.close();
            return localByteArrayOutputStream.toByteArray();
        }
        catch (IOException paramString1)
        {
            for (;;)
            {
                LogFinalUtils.logForException(paramString1);
            }
        }
    }

    public static boolean isGzip(byte[] paramArrayOfByte)
    {
        return (paramArrayOfByte[0] << 8 | paramArrayOfByte[1] & 0xFF) == 8075;
    }

    public static String uncompressToString(byte[] paramArrayOfByte)
    {
        return uncompressToString(paramArrayOfByte, "UTF-8");
    }

    public static String uncompressToString(byte[] paramArrayOfByte, String paramString)
    {
        if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
            return null;
        }
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        paramArrayOfByte = new ByteArrayInputStream(paramArrayOfByte);
        try
        {
            paramArrayOfByte = new GZIPInputStream(paramArrayOfByte);
            byte[] arrayOfByte = new byte['��'];
            for (;;)
            {
                int i = paramArrayOfByte.read(arrayOfByte);
                if (i < 0) {
                    break;
                }
                localByteArrayOutputStream.write(arrayOfByte, 0, i);
            }
            paramArrayOfByte = localByteArrayOutputStream.toString(paramString);
        }
        catch (IOException paramArrayOfByte)
        {
            LogFinalUtils.logForException(paramArrayOfByte);
            return null;
        }
        return paramArrayOfByte;
    }
}

