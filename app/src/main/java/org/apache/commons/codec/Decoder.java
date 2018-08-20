package org.apache.commons.codec;

@Deprecated
public abstract interface Decoder
{
    public abstract Object decode(Object paramObject)
            throws DecoderException;
}
