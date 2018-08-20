package com.olym.mjt.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;

public class BitmapUtil
{
    public static int calculateInSampleSize(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2)
    {
        int k = paramOptions.outHeight;
        int j = paramOptions.outWidth;
        int i = 1;
        if ((k > paramInt2) || (j > paramInt1))
        {
            paramInt2 = Math.round(k / paramInt2);
            paramInt1 = Math.round(j / paramInt1);
            if (paramInt2 >= paramInt1) {
                break label62;
            }
            paramInt1 = paramInt2;
        }
        label62:
        while (paramInt1 < 3)
        {
            i = paramInt1;
            return i;
        }
        if (paramInt1 < 6.5D) {
            return 4;
        }
        if (paramInt1 < 8) {
            return 8;
        }
        return paramInt1;
    }

    public static Bitmap decodeSampledBitmap(String paramString, int paramInt1, int paramInt2)
    {
        BitmapFactory.Options localOptions = new BitmapFactory.Options();
        localOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(paramString, localOptions);
        localOptions.inSampleSize = calculateInSampleSize(localOptions, paramInt1, paramInt2);
        localOptions.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(paramString, localOptions);
    }

    public static Bitmap zoomBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2)
    {
        int i = paramBitmap.getWidth();
        int j = paramBitmap.getHeight();
        Matrix localMatrix = new Matrix();
        localMatrix.postScale(paramInt1 / i, paramInt2 / j);
        return Bitmap.createBitmap(paramBitmap, 0, 0, i, j, localMatrix, true);
    }
}
