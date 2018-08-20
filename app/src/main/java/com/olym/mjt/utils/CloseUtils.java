package com.olym.mjt.utils;

import java.io.Closeable;
import java.io.IOException;

public class CloseUtils
{
    private CloseUtils()
    {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void closeIO(Closeable... paramVarArgs)
    {
        if (paramVarArgs == null) {
            return;
        }
        int j = paramVarArgs.length;
        int i = 0;
        while (i < j)
        {
            Closeable localCloseable = paramVarArgs[i];
            if (localCloseable != null) {}
            try
            {
                localCloseable.close();
                i += 1;
            }
            catch (IOException localIOException)
            {
                for (;;)
                {
                    localIOException.printStackTrace();
                }
            }
        }
    }

    public static void closeIOQuietly(Closeable... paramVarArgs)
    {
        if (paramVarArgs == null) {
            return;
        }
        int j = paramVarArgs.length;
        int i = 0;
        while (i < j)
        {
            Closeable localCloseable = paramVarArgs[i];
            if (localCloseable != null) {}
            try
            {
                localCloseable.close();
                i += 1;
            }
            catch (IOException localIOException)
            {
                for (;;) {}
            }
        }
    }
}
