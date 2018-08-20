package org.apache.commons.codec;

@Deprecated
public abstract interface BinaryEncoder
        extends Encoder
{
    public abstract byte[] encode(byte[] paramArrayOfByte)
            throws EncoderException;
}
