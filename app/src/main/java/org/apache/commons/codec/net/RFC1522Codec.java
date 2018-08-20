package org.apache.commons.codec.net;

import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;

@Deprecated
abstract class RFC1522Codec
{
    RFC1522Codec()
    {
        throw new RuntimeException("Stub!");
    }

    protected String decodeText(String paramString)
            throws DecoderException, UnsupportedEncodingException
    {
        throw new RuntimeException("Stub!");
    }

    protected abstract byte[] doDecoding(byte[] paramArrayOfByte)
            throws DecoderException;

    protected abstract byte[] doEncoding(byte[] paramArrayOfByte)
            throws EncoderException;

    protected String encodeText(String paramString1, String paramString2)
            throws EncoderException, UnsupportedEncodingException
    {
        throw new RuntimeException("Stub!");
    }

    protected abstract String getEncoding();
}

