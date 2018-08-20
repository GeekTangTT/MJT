package android.backport.webp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;

public final class WebPFactory
{
    static
    {
        System.loadLibrary("webpbackport");
    }

    public static native Bitmap nativeDecodeByteArray(byte[] paramArrayOfByte, BitmapFactory.Options paramOptions);

    public static native Bitmap nativeDecodeFile(String paramString, BitmapFactory.Options paramOptions);

    public static native byte[] nativeEncodeBitmap(Bitmap paramBitmap, int paramInt);
}
