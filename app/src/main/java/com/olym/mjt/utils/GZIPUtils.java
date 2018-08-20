package com.olym.mjt.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.zip.GZIPInputStream;

public class GZIPUtils
{
    public static byte[] uncompress(byte[] paramArrayOfByte)
    {
        if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
            return null;
        }
        localByteArrayOutputStream = new ByteArrayOutputStream();
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
            return localByteArrayOutputStream.toByteArray();
        }
        catch (IOException paramArrayOfByte)
        {
            System.out.println("gzip uncompress error.");
        }
    }
}
