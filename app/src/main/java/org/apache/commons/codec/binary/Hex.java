package org.apache.commons.codec.binary;

import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;

@Deprecated
public class Hex
        implements BinaryEncoder, BinaryDecoder
{
    public Hex()
    {
        throw new RuntimeException("Stub!");
    }

    public static byte[] decodeHex(char[] paramArrayOfChar)
            throws DecoderException
    {
        throw new RuntimeException("Stub!");
    }

    public static char[] encodeHex(byte[] paramArrayOfByte)
    {
        throw new RuntimeException("Stub!");
    }

    protected static int toDigit(char paramChar, int paramInt)
            throws DecoderException
    {
        throw new RuntimeException("Stub!");
    }

    public Object decode(Object paramObject)
            throws DecoderException
    {
        throw new RuntimeException("Stub!");
    }

    public byte[] decode(byte[] paramArrayOfByte)
            throws DecoderException
    {
        throw new RuntimeException("Stub!");
    }

    public Object encode(Object paramObject)
            throws EncoderException
    {
        throw new RuntimeException("Stub!");
    }

    public byte[] encode(byte[] paramArrayOfByte)
    {
        throw new RuntimeException("Stub!");
    }
}
