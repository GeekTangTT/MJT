package com.olym.mjt.utils;

import java.lang.reflect.Array;

public class ArrayUtils
{
    private static final int CACHE_SIZE = 73;
    private static Object[] EMPTY = new Object[0];
    private static Object[] sCache = new Object[73];

    public static boolean contains(int[] paramArrayOfInt, int paramInt)
    {
        boolean bool2 = false;
        int j = paramArrayOfInt.length;
        int i = 0;
        for (;;)
        {
            boolean bool1 = bool2;
            if (i < j)
            {
                if (paramArrayOfInt[i] == paramInt) {
                    bool1 = true;
                }
            }
            else {
                return bool1;
            }
            i += 1;
        }
    }

    public static <T> boolean contains(T[] paramArrayOfT, T paramT)
    {
        int j = paramArrayOfT.length;
        int i = 0;
        while (i < j)
        {
            T ? = paramArrayOfT[i];
            if (? == null)
            {
                if (paramT != null) {}
            }
      else {
            while ((paramT != null) && (?.equals(paramT))) {
                return true;
            }
        }
            i += 1;
        }
        return false;
    }

    public static <T> T[] emptyArray(Class<T> paramClass)
    {
        if (paramClass == Object.class) {
            return (Object[])EMPTY;
        }
        int i = (System.identityHashCode(paramClass) / 8 & 0x7FFFFFFF) % 73;
        Object localObject2 = sCache[i];
        Object localObject1;
        if (localObject2 != null)
        {
            localObject1 = localObject2;
            if (localObject2.getClass().getComponentType() == paramClass) {}
        }
        else
        {
            localObject1 = Array.newInstance(paramClass, 0);
            sCache[i] = localObject1;
        }
        return (Object[])localObject1;
    }

    public static boolean equals(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
    {
        if (paramArrayOfByte1 == paramArrayOfByte2) {}
        for (;;)
        {
            return true;
            if ((paramArrayOfByte1 == null) || (paramArrayOfByte2 == null) || (paramArrayOfByte1.length < paramInt) || (paramArrayOfByte2.length < paramInt)) {
                return false;
            }
            int i = 0;
            while (i < paramInt)
            {
                if (paramArrayOfByte1[i] != paramArrayOfByte2[i]) {
                    return false;
                }
                i += 1;
            }
        }
    }

    public static int idealBooleanArraySize(int paramInt)
    {
        return idealByteArraySize(paramInt);
    }

    public static int idealByteArraySize(int paramInt)
    {
        int i = 4;
        for (;;)
        {
            int j = paramInt;
            if (i < 32)
            {
                if (paramInt <= (1 << i) - 12) {
                    j = (1 << i) - 12;
                }
            }
            else {
                return j;
            }
            i += 1;
        }
    }

    public static int idealCharArraySize(int paramInt)
    {
        return idealByteArraySize(paramInt * 2) / 2;
    }

    public static int idealFloatArraySize(int paramInt)
    {
        return idealByteArraySize(paramInt * 4) / 4;
    }

    public static int idealIntArraySize(int paramInt)
    {
        return idealByteArraySize(paramInt * 4) / 4;
    }

    public static int idealLongArraySize(int paramInt)
    {
        return idealByteArraySize(paramInt * 8) / 8;
    }

    public static int idealObjectArraySize(int paramInt)
    {
        return idealByteArraySize(paramInt * 4) / 4;
    }

    public static int idealShortArraySize(int paramInt)
    {
        return idealByteArraySize(paramInt * 2) / 2;
    }
}
