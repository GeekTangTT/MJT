package org.apache.commons.codec.net;

import java.io.UnsupportedEncodingException;
import java.util.BitSet;
import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringDecoder;
import org.apache.commons.codec.StringEncoder;

@Deprecated
public class QuotedPrintableCodec
        implements BinaryEncoder, BinaryDecoder, StringEncoder, StringDecoder
{
    public QuotedPrintableCodec()
    {
        throw new RuntimeException("Stub!");
    }

    public QuotedPrintableCodec(String paramString)
    {
        throw new RuntimeException("Stub!");
    }

    public static final byte[] decodeQuotedPrintable(byte[] paramArrayOfByte)
            throws DecoderException
    {
        throw new RuntimeException("Stub!");
    }

    public static final byte[] encodeQuotedPrintable(BitSet paramBitSet, byte[] paramArrayOfByte)
    {
        throw new RuntimeException("Stub!");
    }

    public Object decode(Object paramObject)
            throws DecoderException
    {
        throw new RuntimeException("Stub!");
    }

    public String decode(String paramString)
            throws DecoderException
    {
        throw new RuntimeException("Stub!");
    }

    public String decode(String paramString1, String paramString2)
            throws DecoderException, UnsupportedEncodingException
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

    public String encode(String paramString)
            throws EncoderException
    {
        throw new RuntimeException("Stub!");
    }

    public String encode(String paramString1, String paramString2)
            throws UnsupportedEncodingException
    {
        throw new RuntimeException("Stub!");
    }

    public byte[] encode(byte[] paramArrayOfByte)
    {
        throw new RuntimeException("Stub!");
    }

    public String getDefaultCharset()
    {
        throw new RuntimeException("Stub!");
    }
}
