package org.apache.commons.codec;

@Deprecated
public abstract interface Encoder
{
    public abstract Object encode(Object paramObject)
            throws EncoderException;
}
