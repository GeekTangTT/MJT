package org.apache.commons.codec;

@Deprecated
public abstract interface BinaryDecoder
        extends Decoder
{
    public abstract byte[] decode(byte[] paramArrayOfByte)
            throws DecoderException;
}
