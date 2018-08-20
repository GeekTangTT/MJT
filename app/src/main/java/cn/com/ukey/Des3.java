package cn.com.ukey;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class Des3
{
    public Des3()
            throws Exception
    {}

    public static byte[] des3DecodeCBC(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
            throws Exception
    {
        paramArrayOfByte1 = new DESedeKeySpec(paramArrayOfByte1);
        paramArrayOfByte1 = SecretKeyFactory.getInstance("desede").generateSecret(paramArrayOfByte1);
        Cipher localCipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        localCipher.init(2, paramArrayOfByte1, new IvParameterSpec(paramArrayOfByte2));
        return localCipher.doFinal(paramArrayOfByte3);
    }

    public static byte[] des3DecodeECB(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
            throws Exception
    {
        paramArrayOfByte1 = new DESedeKeySpec(paramArrayOfByte1);
        paramArrayOfByte1 = SecretKeyFactory.getInstance("desede").generateSecret(paramArrayOfByte1);
        Cipher localCipher = Cipher.getInstance("desede/ECB/PKCS5Padding");
        localCipher.init(2, paramArrayOfByte1);
        return localCipher.doFinal(paramArrayOfByte2);
    }

    public static byte[] des3EncodeCBC(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
            throws Exception
    {
        paramArrayOfByte1 = new DESedeKeySpec(paramArrayOfByte1);
        paramArrayOfByte1 = SecretKeyFactory.getInstance("desede").generateSecret(paramArrayOfByte1);
        Cipher localCipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        localCipher.init(1, paramArrayOfByte1, new IvParameterSpec(paramArrayOfByte2));
        return localCipher.doFinal(paramArrayOfByte3);
    }

    public static byte[] des3EncodeECB(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
            throws Exception
    {
        paramArrayOfByte1 = new DESedeKeySpec(paramArrayOfByte1);
        paramArrayOfByte1 = SecretKeyFactory.getInstance("desede").generateSecret(paramArrayOfByte1);
        Cipher localCipher = Cipher.getInstance("desede/ECB/PKCS5Padding");
        localCipher.init(1, paramArrayOfByte1);
        return localCipher.doFinal(paramArrayOfByte2);
    }
}
