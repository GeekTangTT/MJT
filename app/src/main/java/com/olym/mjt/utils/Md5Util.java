package com.olym.mjt.utils;

import com.lc.methodex.LogFinalUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util
{
    public static String toMD5(String paramString)
    {
        StringBuffer localStringBuffer = new StringBuffer();
        try
        {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramString.getBytes());
            paramString = localMessageDigest.digest();
            int i = 0;
            while (i < paramString.length)
            {
                int k = paramString[i];
                int j = k;
                if (k < 0) {
                    j = k + 256;
                }
                if (j < 16) {
                    localStringBuffer.append("0");
                }
                localStringBuffer.append(Integer.toHexString(j));
                i += 1;
            }
            paramString = localStringBuffer.toString();
            return paramString;
        }
        catch (NoSuchAlgorithmException paramString)
        {
            paramString.printStackTrace();
            LogFinalUtils.logForException(paramString);
        }
        return null;
    }
}
